//package com.zkteco.bionest.samples.grpc;
//
//import com.zkteco.bionest.samples.grpc.loadbalancing.zoo.ZkNameResolverProvider;
//import io.grpc.ManagedChannel;
//import io.grpc.ManagedChannelBuilder;
//import io.grpc.StatusRuntimeException;
//import io.grpc.examples.routeguide.Feature;
//import io.grpc.examples.routeguide.RouteGuideGrpc;
//import io.grpc.examples.routeguide.RouteGuideGrpc.RouteGuideBlockingStub;
//
//public class RouteGuideClient {
//
//	private final ManagedChannel channel;
//
//	private final RouteGuideBlockingStub blockingStub;
//
//	public RouteGuideClient(String zkAddr) {
//		this(ManagedChannelBuilder.forTarget(zkAddr).defaultLoadBalancingPolicy("round_robin")
////				.loadBalancerFactory(RoundRobinLoadBalancerFactory.getInstance())
//				.nameResolverFactory(new ZkNameResolverProvider())
//				.usePlaintext());
//	}
//
//	/** Construct client for accessing the server using the existing channel. */
//	RouteGuideClient(ManagedChannelBuilder<?> channelBuilder) {
//		channel = channelBuilder.build();
//		blockingStub = RouteGuideGrpc.newBlockingStub(channel);
//	}
//
//	/**
//	 * Greeter client. First argument of {@code args} is the address of the
//	 * Zookeeper ensemble. The client keeps making simple RPCs until interrupted
//	 * with a Ctrl-C.
//	 */
//	public static void main(String[] args) throws Exception {
////		if (args.length != 1) {
////			System.out.println("Usage: helloworld_client zk://ADDR:PORT");
////			return;
////		}
//		RouteGuideClient client = new RouteGuideClient("zk://192.168.241.200:2181");
//		try {
//			while (true) {
//				client.greet();
//				Thread.sleep(1000);
//			}
//		}
//		finally {
////			client.shutdown();
//		}
//	}
//
//	/** Say hello to server. */
//	public void greet() {
//		Feature response;
//		try {
//			response = blockingStub.getFeature(null);
//			System.out.println("response is " + response);
//		}
//		catch (StatusRuntimeException e) {
//			return;
//		}
//	}
//
//
////	public static void main(String[] args) throws Exception{
////		ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8980").usePlaintext().build();
////
////		RouteGuideBlockingStub blockingStub = RouteGuideGrpc.newBlockingStub(channel);
////		RouteGuideStub asyncStub = RouteGuideGrpc.newStub(channel);
////
////		Point point = Point.newBuilder().setLatitude(1).setLongitude(1).build();
////		Feature feature = blockingStub.getFeature(point);
////
////		System.out.println(feature.getName());
////
////		Iterator<Feature> featureIterator = blockingStub.listFeatures(null);
////		while (featureIterator.hasNext()) {
////			System.out.println(featureIterator.next().getName());
////		}
////
////		final CountDownLatch finishLatch = new CountDownLatch(1);
////		// client stream test
////		StreamObserver<RouteSummary> responseObserver = new StreamObserver<RouteSummary>() {
////
////
////
////			@Override
////			public void onNext(RouteSummary value) {
////				System.out.println("rsponsessssss");
////			}
////
////			@Override
////			public void onError(Throwable t) {
////
////			}
////
////			@Override
////			public void onCompleted() {
////				finishLatch.countDown();
////			}
////		};
////		StreamObserver<Point> requestObserver = asyncStub.recordRoute(responseObserver);
////
////		for (int i = 0; i < 100; i++) {
////			requestObserver.onNext(null);
////		}
////
////		requestObserver.onCompleted();
////
////		finishLatch.await();
////
////	}
//
//}
