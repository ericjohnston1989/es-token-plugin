/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.action.trainnaivebayes;

import org.elasticsearch.action.ActionRequest;
import org.elasticsearch.action.ActionRequestValidationException;
import org.elasticsearch.action.ValidateActions;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;

import java.io.IOException;

public class TrainNaiveBayesRequest extends ActionRequest<TrainNaiveBayesRequest> {

    private String source;
    private String id;

    public TrainNaiveBayesRequest() {

    }
    public TrainNaiveBayesRequest(String source) {
        this.source = source;

    }

    @Override
    public ActionRequestValidationException validate() {
        ActionRequestValidationException validationException = null;
        if (source == null) {
            validationException = ValidateActions.addValidationError("train naive bayes needs a source", validationException);
        }
        return validationException;
    }

    @Override
    public void readFrom(StreamInput in) throws IOException {
        super.readFrom(in);
        source = in.readString();
        id = in.readOptionalString();

    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        super.writeTo(out);
        out.writeString(source);
        out.writeOptionalString(id);
    }


    public TrainNaiveBayesRequest source(String source) {
        this.source = source;
        return this;
    }

    public String source() {
        return source;
    }

    public void id(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }
}
