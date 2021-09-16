//package com.zkteco.bionest.samples.grpc.loadbalancing.zoo;
//
//import java.net.InetSocketAddress;
//import java.net.SocketAddress;
//import java.net.URI;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.CountDownLatch;
//
//import io.grpc.Attributes;
//import io.grpc.EquivalentAddressGroup;
//import io.grpc.NameResolver;
//import io.grpc.NameResolverProvider;
//import org.apache.zookeeper.WatchedEvent;
//import org.apache.zookeeper.Watcher;
//import org.apache.zookeeper.Watcher.Event.KeeperState;
//import org.apache.zookeeper.ZooKeeper;
//import org.apache.zookeeper.data.Stat;
//
//class ZkNameResolver extends NameResolver implements Watcher {
//	/** Hard-coded path to the ZkNode that knows about servers.
//	 * Note this must match with the path used by HelloWorldServer */
//	public static final String PATH = "/grpc_hello_world_service";
//
//	/** 2 seconds to indicate that client disconnected */
//	public static final int TIMEOUT_MS = 2000;
//
//	private final URI zkUri;
//
//	private ZooKeeper zoo;
//
//	private Listener listener;
//
//	/**
//	 * The callback from Zookeeper when servers are added/removed.
//	 */
//	@Override
//	public void process(WatchedEvent we) {
//		if (we.getType() == Event.EventType.None) {
//			System.out.println("Connection expired");
//		}
//		else {
//			try {
//				List<String> servers = zoo.getChildren(PATH, false);
//				AddServersToListener(servers);
//				zoo.getChildren(PATH, this);
//			}
//			catch (Exception ex) {
//				System.out.println(ex.getMessage());
//			}
//		}
//	}
//
//	private void AddServersToListener(List<String> servers) {
//		List<EquivalentAddressGroup> addrs = new ArrayList<EquivalentAddressGroup>();
//		System.out.println("Updating server list");
//		for (String child : servers) {
//			try {
//				System.out.println("Online: " + child);
//				URI uri = new URI("dummy://" + child);
//				// Convert "host:port" into host and port
//				String host = uri.getHost();
//				int port = uri.getPort();
//				List<SocketAddress> sockaddrs_list = new ArrayList<SocketAddress>();
//				sockaddrs_list.add(new InetSocketAddress(host, port));
//				addrs.add(new EquivalentAddressGroup(sockaddrs_list));
//			}
//			catch (Exception ex) {
//				System.out.println("Unparsable server address: " + child);
//				System.out.println(ex.getMessage());
//			}
//		}
//		if (addrs.size() > 0) {
//			listener.onAddresses(addrs, Attributes.EMPTY);
//		}
//		else {
//			System.out.println("No servers online. Keep looking");
//		}
//	}
//
//
//	public ZkNameResolver(URI zkUri) {
//		this.zkUri = zkUri;
//	}
//
//	@Override
//	public String getServiceAuthority() {
//		return zkUri.getAuthority();
//	}
//
//	@Override
//	public void start(Listener listener) {
//		this.listener = listener;
//		final CountDownLatch connectedSignal = new CountDownLatch(1);
//		try {
//			String zkaddr = zkUri.getHost() + ":" + zkUri.getPort();
//			System.out.println("Connecting to Zookeeper Address " + zkaddr);
//
//			this.zoo = new ZooKeeper(zkaddr, TIMEOUT_MS, new Watcher() {
//				public void process(WatchedEvent we) {
//					if (we.getState() == KeeperState.SyncConnected) {
//						connectedSignal.countDown();
//					}
//				}
//			});
//			connectedSignal.await();
//			System.out.println("Connected!");
//		}
//		catch (Exception e) {
//			System.out.println("Failed to connect");
//			return;
//		}
//
//
//		try {
//			Stat stat = zoo.exists(PATH, true);
//			if (stat == null) {
//				System.out.println("PATH does not exist.");
//			}
//			else {
//				System.out.println("PATH exists");
//			}
//		}
//		catch (Exception e) {
//			System.out.println("Failed to get stat");
//			return;
//		}
//
//		try {
//			final CountDownLatch connectedSignal1 = new CountDownLatch(1);
//			List<String> servers = zoo.getChildren(PATH, this);
//			AddServersToListener(servers);
//		}
//		catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//	}
//
//	@Override
//	public void shutdown() {
//	}
//}
//
