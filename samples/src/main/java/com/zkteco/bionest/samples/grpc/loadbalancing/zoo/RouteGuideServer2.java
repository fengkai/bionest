//package com.zkteco.bionest.samples.grpc.loadbalancing.zoo;
//
//import java.io.IOException;
//import java.net.URL;
//import java.util.Collection;
//import java.util.concurrent.TimeUnit;
//
//import com.zkteco.bionest.samples.grpc.RouteGuideService;
//import com.zkteco.bionest.samples.grpc.RouteGuideUtil;
//import io.grpc.Server;
//import io.grpc.ServerBuilder;
//import io.grpc.examples.routeguide.Feature;
//
//public class RouteGuideServer2 {
//
//	private final int port;
//	private final Server server;
//
//
//	public RouteGuideServer2(int port) throws IOException {
//		this(port, RouteGuideUtil.getDefaultFeaturesFile());
//	}
//
//	/** Create a RouteGuide server listening on {@code port} using {@code featureFile} database. */
//	public RouteGuideServer2(int port, URL featureFile) throws IOException {
//		this(ServerBuilder.forPort(port), port,null);
//	}
//
//	/** Create a RouteGuide server using serverBuilder as a base and features as data. */
//	public RouteGuideServer2(ServerBuilder<?> serverBuilder, int port, Collection<Feature> features) {
//		this.port = port;
//		server = serverBuilder.addService(new RouteGuideService(null))
//				.build();
//	}
//
//	/**
//	 * Main method.  This comment makes the linter happy.
//	 */
//	public static void main(String[] args) throws Exception {
//		RouteGuideServer2 server = new RouteGuideServer2(8981);
//
//		ZookeeperConnection zk_conn = new ZookeeperConnection();
//
//		if (!zk_conn.connect("zk://192.168.241.200:2181", "localhost", "8981")) {
//			System.out.println("Shit, i can't connect!");
//			return;
//		}
//
//		server.start();
//		server.blockUntilShutdown();
//	}
//
//	/** Start serving requests. */
//	public void start() throws IOException {
//		server.start();
//		Runtime.getRuntime().addShutdownHook(new Thread() {
//			@Override
//			public void run() {
//				// Use stderr here since the logger may have been reset by its JVM shutdown hook.
//				System.err.println("*** shutting down gRPC server since JVM is shutting down");
//				try {
//					RouteGuideServer2.this.stop();
//				} catch (InterruptedException e) {
//					e.printStackTrace(System.err);
//				}
//				System.err.println("*** server shut down");
//			}
//		});
//	}
//
//	/** Stop serving requests and shutdown resources. */
//	public void stop() throws InterruptedException {
//		if (server != null) {
//			server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
//		}
//	}
//
//	/**
//	 * Await termination on the main thread since the grpc library uses daemon threads.
//	 */
//	private void blockUntilShutdown() throws InterruptedException {
//		if (server != null) {
//			server.awaitTermination();
//		}
//	}
//
//}
