//package com.zkteco.bionest.samples.grpc.loadbalancing.zoo;
//
//import java.io.IOException;
//import java.net.URI;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.concurrent.CountDownLatch;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.zookeeper.CreateMode;
//import org.apache.zookeeper.WatchedEvent;
//import org.apache.zookeeper.Watcher;
//import org.apache.zookeeper.Watcher.Event.KeeperState;
//import org.apache.zookeeper.ZooDefs;
//import org.apache.zookeeper.ZooKeeper;
//import org.apache.zookeeper.data.Stat;
//
//public class ZookeeperConnection {
//
//	private ZooKeeper zoo;
//
//	/**
//	 * Connects to a zookeeper ensemble in zkUriStr.
//	 * serverIp and portStr are the IP/Port of this server.
//	 */
//	public boolean connect(String zkUriStr, String serverIp, String portStr)
//			throws IOException, InterruptedException {
//		final CountDownLatch connectedSignal = new CountDownLatch(1);
//		String zkhostport;
//		try {
//			URI zkUri = new URI(zkUriStr);
//			zkhostport = zkUri.getHost() + ":" + zkUri.getPort();
//		}
//		catch (Exception e) {
//			System.out.println("Could not parse zk URI " + zkUriStr);
//			return false;
//		}
//
//		zoo = new ZooKeeper(zkhostport, 5000, new Watcher() {
//			public void process(WatchedEvent we) {
//				if (we.getState() == KeeperState.SyncConnected) {
//					connectedSignal.countDown();
//				}
//			}
//		});
//		/* Wait for zookeeper connection */
//		connectedSignal.await();
//
//		String path = "/grpc_hello_world_service";
//		Stat stat;
//		String currTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
//		try {
//			stat = zoo.exists(path, true);
//			if (stat == null) {
//				zoo.create(path, currTime.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//			}
//		}
//		catch (Exception e) {
//			System.out.println("Failed to create path");
//			return false;
//		}
//
//		String server_addr = path + "/" + serverIp + ":" + portStr;
//		try {
//			stat = zoo.exists(server_addr, true);
//			if (stat == null) {
//				try {
//					zoo.create(server_addr, currTime.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
//				}
//				catch (Exception e) {
//					System.out.println("Failed to create server_data");
//					return false;
//				}
//			}
//			else {
//				try {
//					zoo.setData(server_addr, currTime.getBytes(), stat.getVersion());
//				}
//				catch (Exception e) {
//					System.out.println("Failed to update server_data");
//					return false;
//				}
//			}
//		}
//		catch (Exception e) {
//			System.out.println("Failed to add server_data");
//			return false;
//		}
//		return true;
//	}
//
//	// Method to disconnect from zookeeper server
//	public void close() throws InterruptedException {
//		zoo.close();
//	}
//
//}
