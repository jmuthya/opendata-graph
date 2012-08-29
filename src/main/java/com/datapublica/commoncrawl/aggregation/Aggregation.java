/*
 * Copyright (C) by Data Publica, All Rights Reserved.
 */
package com.datapublica.commoncrawl.aggregation;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;

import com.datapublica.commoncrawl.utils.JobHelper;
import com.datapublica.commoncrawl.utils.Loggers;

public class Aggregation {

    private final static Log LOG = LogFactory.getLog(Aggregation.class);

    private static final String DP_BUCKET_PREFIX = "s3n://dp-commoncrawl/";

    public static void main(String[] args) {

        Loggers.setup();

        String aggregationInput = null;
        String aggregationOutput = null;

        // Run a numeric aggregation on specific inputs
        if (args.length == 2) {

            // Append the input path with the first argument
            aggregationInput = DP_BUCKET_PREFIX + args[0];
            // Append the output path with the second argument
            aggregationOutput = DP_BUCKET_PREFIX + args[1];

            // Run job
            RunNumericAggregation(aggregationInput, aggregationOutput);

        } else {
            LOG.warn("Usage : inputPath outputPath");
        }

    }

    public static void RunNumericAggregation(String aggregationInput, String aggregationOutput) {
        JobConf aggregationJob = new JobConf();

        JobHelper.ConfigureNumericOutputJob(aggregationJob, aggregationInput, aggregationOutput);
        aggregationJob.setMapperClass(NumericAggregationMapper.class);

        try {
            LOG.info("Launching aggregation job");
            LOG.info("Start time : " + System.currentTimeMillis());
            JobClient.runJob(aggregationJob);
            LOG.info("Finish time : " + System.currentTimeMillis());
            LOG.info("Aggregation finished successfully ");
        } catch (IOException e) {
            LOG.error("Failed to run the aggregation job, caught error : " + e);
        }
    }

    public static void RunTextualAggregation(String aggregationInput, String aggregationOutput) {
        JobConf aggregationJob = new JobConf();

        JobHelper.ConfigureTextualOutputJob(aggregationJob, aggregationInput, aggregationOutput);
        aggregationJob.setMapperClass(TextualAggregationMapper.class);

        try {
            LOG.info("Launching aggregation job");
            LOG.info("Start time : " + System.currentTimeMillis());
            JobClient.runJob(aggregationJob);
            LOG.info("Finish time : " + System.currentTimeMillis());
            LOG.info("Aggregation finished successfully ");
        } catch (IOException e) {
            LOG.error("Failed to run the aggregation job, caught error : " + e);
        }
    }

    public static void RunNumericAggregationWithFilter(String aggregationInput, String aggregationOutput) {
        JobConf aggregationJob = new JobConf();

        JobHelper.ConfigureNumericOutputJob(aggregationJob, aggregationInput, aggregationOutput);
        aggregationJob.setMapperClass(NumericAggregationWithFilterMapper.class);

        try {
            LOG.info("Launching aggregation job");
            LOG.info("Start time : " + System.currentTimeMillis());
            JobClient.runJob(aggregationJob);
            LOG.info("Finish time : " + System.currentTimeMillis());
            LOG.info("Aggregation finished successfully ");
        } catch (IOException e) {
            LOG.error("Failed to run the aggregation job, caught error : " + e);
        }
    }
}
