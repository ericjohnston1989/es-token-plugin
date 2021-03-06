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

package org.elasticsearch.plugin;

import org.elasticsearch.action.ActionModule;
import org.elasticsearch.action.allterms.AllTermsAction;
import org.elasticsearch.action.allterms.TransportAllTermsAction;
import org.elasticsearch.action.allterms.TransportAllTermsShardAction;
import org.elasticsearch.action.preparespec.PrepareSpecAction;
import org.elasticsearch.action.preparespec.TransportPrepareSpecAction;
import org.elasticsearch.action.trainnaivebayes.TrainNaiveBayesAction;
import org.elasticsearch.action.trainnaivebayes.TransportTrainNaiveBayesAction;
import org.elasticsearch.index.mapper.token.AnalyzedTextFieldMapper;
import org.elasticsearch.indices.IndicesModule;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.rest.RestModule;
import org.elasticsearch.rest.action.allterms.RestAllTermsAction;
import org.elasticsearch.rest.action.preparespec.RestPrepareSpecAction;
import org.elasticsearch.rest.action.storemodel.RestStoreModelAction;
import org.elasticsearch.rest.action.trainnaivebayes.RestTrainNaiveBayesAction;
import org.elasticsearch.script.NaiveBayesUpdateScript;
import org.elasticsearch.script.ScriptModule;
import org.elasticsearch.script.pmml.PMMLModelScriptEngineService;
import org.elasticsearch.script.pmml.VectorScriptEngineService;
import org.elasticsearch.search.SearchModule;
import org.elasticsearch.search.fetch.analyzedtext.AnalyzedTextFetchSubPhase;
import org.elasticsearch.search.fetch.termvectors.TermVectorsFetchSubPhase;

/**
 *
 */
public class TokenPlugin extends Plugin {

    @Override
    public String name() {
        return "token-plugin";
    }

    @Override
    public String description() {
        return "Tools for https://github.com/costin/poc";
    }


    public void onModule(ScriptModule module) {
        // Register each script that we defined in this plugin
        module.registerScript(NaiveBayesUpdateScript.SCRIPT_NAME, NaiveBayesUpdateScript.Factory.class);
        module.addScriptEngine(VectorScriptEngineService.class);
        module.addScriptEngine(PMMLModelScriptEngineService.class);
    }

    public void onModule(ActionModule module) {
        module.registerAction(AllTermsAction.INSTANCE, TransportAllTermsAction.class,
                TransportAllTermsShardAction.class);
        module.registerAction(PrepareSpecAction.INSTANCE, TransportPrepareSpecAction.class);
        module.registerAction(TrainNaiveBayesAction.INSTANCE, TransportTrainNaiveBayesAction.class);
    }

    public void onModule(RestModule module) {
        module.addRestAction(RestAllTermsAction.class);
        module.addRestAction(RestPrepareSpecAction.class);
        module.addRestAction(RestStoreModelAction.class);
        module.addRestAction(RestTrainNaiveBayesAction.class);
    }

    public void onModule(IndicesModule indicesModule) {
        indicesModule.registerMapper(AnalyzedTextFieldMapper.CONTENT_TYPE, new AnalyzedTextFieldMapper.TypeParser());
    }

    public void onModule(SearchModule searchModule) {
        searchModule.registerFetchSubPhase(TermVectorsFetchSubPhase.class);
        searchModule.registerFetchSubPhase(AnalyzedTextFetchSubPhase.class);
    }
}
