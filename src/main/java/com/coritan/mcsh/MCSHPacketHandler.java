package com.coritan.mcsh;

import com.coritan.mcsh.geyser.GeyserUtils;
import com.coritan.mcsh.provider.PacketProvider;
import com.coritan.mcsh.provider.PlayerProvider;
import com.coritan.mcsh.util.exception.parse.InvalidPayloadException;
import com.coritan.mcsh.util.exception.parse.TimestampValidationException;
import com.coritan.mcsh.util.exception.phase.HandshakeException;
import com.coritan.mcsh.util.exception.phase.InvalidSecretException;
import com.coritan.mcsh.util.validation.cidr.CIDRValidator;
import com.coritan.mcsh.util.validation.timestamp.TimestampValidator;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Packet handler for MCSH's plugins
 */
public class MCSHPacketHandler {

	private final MCSHPlugin plugin;

	private TimestampValidator timestampValidator;
	private CIDRValidator cidrValidator;

	/**
	 * Creates an instance of the MCSHPacketHandler
	 *
	 * @param plugin The MCSH plugin
	 */
	public MCSHPacketHandler(MCSHPlugin plugin) {
		this.plugin = plugin;
		initValidators();
	}

	/**
	 * Initiates the validators
	 */
	private void initValidators() {
		timestampValidator = TimestampValidator.createDefault(plugin);
		cidrValidator = new CIDRValidator(plugin);
	}

	/**
	 * Handles the packet and player manipulation
	 * for the initial handshake from MCSH
	 * servers
	 *
	 * @param packet The handshake packet
	 * @param player The involved player
	 */
	public void handleHandshake(PacketProvider packet, PlayerProvider player) throws HandshakeException {
		try {
			InetAddress inetAddress = InetAddress.getByName(player.getIP());

			String extraData = null;
			String[] payload = packet.getPayloadString().split("///");

			if (payload.length != 3 && payload.length != 4)
				if (cidrValidator.validate(inetAddress))
					return;
				else
					throw new InvalidPayloadException("length: " + payload.length + ", payload: " + Arrays.toString(payload) + ", raw payload: " + packet.getPayloadString());

			int nullIndex;
			int lastIndex = payload.length - 1;
			if ((nullIndex = payload[lastIndex].indexOf('\0')) != -1) {
				String originalData = payload[lastIndex];
				payload[lastIndex] = originalData.substring(0, nullIndex);
				extraData = originalData.substring(nullIndex);
			}

			String hostname = payload[0];
			String ipData = payload[1];
			int timestamp = Integer.parseInt(payload[2]);
			String signature = payload.length == 4 ? payload[3] : null;

			String[] ipParts;
			String host;
			int port;

			if (timestamp == 0 && GeyserUtils.GEYSER_SUPPORT_ENABLED && payload.length == 4) {
				ipData = payload[0];
				signature = payload[1];
				hostname = payload[3];

				ipParts = ipData.split(":");
				host = ipParts[0];
				port = Integer.parseInt(ipParts[1]);

				if (!signature.equals(GeyserUtils.SESSION_SECRET)) {
					throw new InvalidSecretException("Invalid secret: " + signature);
				}
			} else {
				ipParts = ipData.split(":");
				host = ipParts[0];
				port = Integer.parseInt(ipParts[1]);

				if (!timestampValidator.validate(timestamp))
					throw new TimestampValidationException(timestampValidator, timestamp);
			}

			InetSocketAddress newIP = new InetSocketAddress(host, port);
			player.setIP(newIP);

			if (extraData != null) hostname = hostname + extraData;

			packet.setPacketHostname(hostname);
		} catch (NumberFormatException | InvalidPayloadException e) {
			plugin.getDebugger().warn(
					"%s[%s/%s] was disconnected because no proxy info was received and only-allow-proxy-connections is enabled. Raw payload = \"%s\"",
					player.getName(), player.getUUID(), player.getIP(), packet.getPayloadString());
			if (plugin.getConfigProvider().isOnlyProxy())
				player.disconnect();

			if (!(e instanceof InvalidPayloadException))
				throw new HandshakeException(new InvalidPayloadException(e));
			else
				throw new HandshakeException(e);
		} catch (TimestampValidationException e) {
			plugin.getDebugger().warn(
					"%s[%s/%s] provided valid handshake information, but timestamp was not valid. " +
							"Provided timestamp: %d vs. system timestamp: %d. Please check your machine time. Timestamp validation mode: %s",
					player.getName(), player.getUUID(), player.getIP(), e.getTimestamp(), e.getTimestampValidator().getUnixTime(), plugin.getConfigProvider().getTimestampValidationMode());
			if (plugin.getConfigProvider().isOnlyProxy())
				player.disconnect();

			throw new HandshakeException(e);
		} catch (UnknownHostException e) {
			if (plugin.getConfigProvider().isOnlyProxy())
				player.disconnect();

			throw new HandshakeException(e);
		} catch (InvalidSecretException e) {
			plugin.getDebugger().warn(
					"%s[%s/%s] provided an invalid session secret when authenticating a geyser connection.",
					player.getName(), player.getUUID(), player.getIP());
			if (plugin.getConfigProvider().isOnlyProxy())
				player.disconnect();

			throw new HandshakeException(e);
		} catch (Exception e) {
			if (plugin.getConfigProvider().isOnlyProxy())
				player.disconnect();

			if (!(e instanceof HandshakeException))
				throw new HandshakeException(e);
			else
				throw e;
		}
	}

}
