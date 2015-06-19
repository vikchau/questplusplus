/**
 *
 */
package shef.mt.features.impl.doclevel;

import java.util.HashSet;
import java.util.StringTokenizer;

import shef.mt.features.impl.DocLevelFeature;
import shef.mt.features.util.Sentence;
import shef.mt.features.util.Doc;
import shef.mt.tools.FileModel;
import shef.mt.tools.Giza;

/**
 * average number of translations per source word in the sentence (threshold in
 * giza1: prob > 0.01) weighted by the frequency of each word in the source
 * corpus
 *
 * @author Catalina Hallett
 *
 *
 */
public class DocLevelFeature1026 extends DocLevelFeature {

    final static Float probThresh = 0.01f;

    public DocLevelFeature1026() {
        setIndex(1026);
        setDescription("average number of translations per source word in the sentence (threshold in giza1: prob > 0.01) weighted by the frequency of each word in the source corpus");
        this.addResource("Giza");
        //res.add("Freq");
    }

    /* (non-Javadoc)
     * @see wlv.mt.features.util.Feature#run(wlv.mt.features.util.Sentence, wlv.mt.features.util.Sentence)
     */
    @Override
    public void run(Sentence source, Sentence target) {
        // TODO Auto-generated method stub


    }

    @Override
    public void run(Doc source, Doc target) {
        float noTokens = 0;
        
        float probSum = 0;
        
        for (int i=0;i<source.getSentences().size();i++){
            String[] tokens = source.getSentence(i).getTokens();
            for (String word : tokens) {
                probSum += Giza.getWordProbabilityCount(word, probThresh) * FileModel.getFrequency(word);
            }
            noTokens+=source.getSentence(i).getNoTokens();
        }
        
        if (noTokens==0){
            setValue(0);
        }else{
            setValue((float) probSum / noTokens);
        }
        
        
    }
}