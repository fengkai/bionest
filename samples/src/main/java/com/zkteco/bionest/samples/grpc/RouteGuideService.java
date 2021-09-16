//package com.zkteco.bionest.samples.grpc;
//
//import java.util.Collection;
//import java.util.Collections;
//import java.util.concurrent.atomic.AtomicInteger;
//
//import io.grpc.examples.routeguide.Feature;
//import io.grpc.examples.routeguide.Point;
//import io.grpc.examples.routeguide.Rectangle;
//import io.grpc.examples.routeguide.RouteGuideGrpc.RouteGuideImplBase;
//import io.grpc.examples.routeguide.RouteSummary;
//import io.grpc.stub.StreamObserver;
//
//public class RouteGuideService extends RouteGuideImplBase {
//
//	private final Collection<Feature> features;
//
//	public RouteGuideService(Collection<Feature> features) {
//		Point point = Point.newBuilder().setLongitude(1).setLatitude(1).build();
//		Feature feature = Feature.newBuilder().setName("test1").setLocation(point).build();
//		this.features = Collections.singletonList(feature);
//	}
//
//	@Override
//	public void getFeature(Point request, StreamObserver<Feature> responseObserver) {
//		responseObserver.onNext(checkFeature(request));
//		responseObserver.onCompleted();
//	}
//
//	@Override
//	public void listFeatures(Rectangle request, StreamObserver<Feature> responseObserver) {
//		for (int i = 0; i < 100; i++) {
//			responseObserver.onNext(Feature.newBuilder().setName("feature" + i).build());
//		}
//		responseObserver.onCompleted();
//	}
//
//	@Override
//	public StreamObserver<Point> recordRoute(StreamObserver<RouteSummary> responseObserver) {
//		return new StreamObserver<Point>() {
//
//			AtomicInteger atomicInteger = new AtomicInteger(0);
//
//			int pointCount;
//			int featureCount;
//			int distance;
//			Point previous;
//			long startTime = System.nanoTime();
//
//			@Override
//			public void onNext(Point value) {
//				System.out.println("client" + atomicInteger.getAndDecrement() );
//			}
//
//			@Override
//			public void onError(Throwable t) {
//
//			}
//
//			@Override
//			public void onCompleted() {
//				responseObserver.onNext((RouteSummary.newBuilder().setPointCount(pointCount)
//						.setFeatureCount(featureCount).setDistance(distance)
//						.setElapsedTime((int) 10).build()));
//				responseObserver.onCompleted();
//			}
//		};
//	}
//
//	private Feature checkFeature(Point location) {
//		for (Feature feature : features) {
//			if (feature.getLocation().getLatitude() == location.getLatitude()
//					&& feature.getLocation().getLongitude() == location.getLongitude()) {
//				return feature;
//			}
//		}
//
//		// No feature was found, return an unnamed feature.
//		return Feature.newBuilder().setName("").setLocation(location).build();
//	}
//
//}
