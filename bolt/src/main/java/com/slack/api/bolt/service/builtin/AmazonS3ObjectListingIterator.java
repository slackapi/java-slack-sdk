package com.slack.api.bolt.service.builtin;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

class AmazonS3ObjectListingIterator implements Iterator<ObjectListing> {

    private final AmazonS3 s3;
    private ObjectListing listing;

    public AmazonS3ObjectListingIterator(AmazonS3 s3, String bucket, String prefix) {
        this.s3 = s3;

        ObjectListing seed = new ObjectListing();
        seed.setTruncated(true);
        seed.setBucketName(bucket);
        seed.setPrefix(prefix);

        this.listing = seed;
    }

    @Override
    public boolean hasNext() {
        return listing.isTruncated();
    }

    @Override
    public ObjectListing next() {
        return listing = s3.listObjects(new ListObjectsRequest()
                .withBucketName(listing.getBucketName())
                .withPrefix(listing.getPrefix())
                .withMarker(listing.getNextMarker())
        );
    }

    public Stream<ObjectListing> toStream() {
        Spliterator<ObjectListing> spliterator = Spliterators.spliteratorUnknownSize(this, 0);
        return StreamSupport.stream(spliterator, false);
    }

}
