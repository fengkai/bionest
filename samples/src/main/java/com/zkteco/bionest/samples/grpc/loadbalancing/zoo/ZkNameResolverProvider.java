//package com.zkteco.bionest.samples.grpc.loadbalancing.zoo;
//
//import java.net.URI;
//
//import io.grpc.NameResolver;
//import io.grpc.NameResolver.Args;
//import io.grpc.NameResolverProvider;
//
//public class ZkNameResolverProvider extends NameResolverProvider {
//
////	@Override
////	public NameResolver newNameResolver(URI targetUri, Attributes params) {
////		return new ZkNameResolver(targetUri);
////	}
//
//	@Override
//	protected int priority() {
//		return 5;
//	}
//
//	@Override
//	protected boolean isAvailable() {
//		return true;
//	}
//
//	@Override
//	public NameResolver newNameResolver(URI targetUri, Args args) {
//		return new ZkNameResolver(targetUri);
//	}
//
//	@Override
//	public String getDefaultScheme() {
//		return "zk";
//	}
//}