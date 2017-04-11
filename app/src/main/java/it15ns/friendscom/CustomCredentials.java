package it15ns.friendscom;

import java.security.Key;
import java.util.concurrent.Executor;

import io.grpc.Attributes;
import io.grpc.CallCredentials;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;

/**
 * Created by danie on 04/04/2017.
 */

public class CustomCredentials implements CallCredentials
{
    String token = "";

    public CustomCredentials(String token) {
        this.token = token;
    }

    @Override
    public void applyRequestMetadata(MethodDescriptor<?, ?> method, Attributes attrs, Executor appExecutor,final MetadataApplier applier)
    {
        try {
            Metadata header = new Metadata();
            Metadata.Key<String> headerKey = Metadata.Key.of("'token'", Metadata.ASCII_STRING_MARSHALLER);
            header.put(headerKey, token);
            applier.apply(header);
        } catch (Throwable e) {
            applier.fail(Status.UNAUTHENTICATED.withCause(e));
        }
    }
}
