package com.coritan.mcsh.bungee.handler;

import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.protocol.packet.Handshake;
import com.coritan.mcsh.provider.PacketProvider;
import com.coritan.mcsh.util.ReflectionUtil;
import com.coritan.mcsh.util.exception.manipulate.PacketManipulationException;

import java.net.InetSocketAddress;

/**
 * A packet wrapper for Bungee
 */
public class BungeePacket implements PacketProvider {

	private final Handshake handshake;
	private final PendingConnection pendingConnection;

	public BungeePacket(Handshake handshake, PendingConnection pendingConnection) {
		this.handshake = handshake;
		this.pendingConnection = pendingConnection;
	}


	@Override
	public String getPayloadString() {
		return handshake.getHost();
	}

	@Override
	public void setPacketHostname(String hostname) throws PacketManipulationException {
		try {
			InetSocketAddress virtualHost = InetSocketAddress.createUnresolved(hostname, handshake.getPort());
			try {
				ReflectionUtil.setFinalField(pendingConnection, "virtualHost", virtualHost);
			} catch (Exception ex) {
				ReflectionUtil.setFinalField(pendingConnection, "vHost", virtualHost);
			}

			ReflectionUtil.setField(handshake, "host", hostname);
		} catch (Exception e) {
			throw new PacketManipulationException(e);
		}
	}

}