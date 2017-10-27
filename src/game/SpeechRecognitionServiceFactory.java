package game;

import processes.ServiceFactory;

/**
 * Abstract the command line tools and the HTK framework.
 * @author szhu842, osim082
 */
public class SpeechRecognitionServiceFactory extends ServiceFactory {
    public static final String PLAY = "play";
    public static final String RECORD = "record";
    public static final String PROCESS = "process";

    /**
     * Contructor. Add the commands we need.
     */
    public SpeechRecognitionServiceFactory() {
        super();
        addProcess(PLAY, "aplay foo.wav");
        addProcess(RECORD, "arecord -d 3 -r 22050 -c 1 -i -t wav -f s16_LE foo.wav");
        addProcess(PROCESS, "HVite -H MaoriNumbers/HMMs/hmm15/macros" +
                " -H MaoriNumbers/HMMs/hmm15/hmmdefs -C MaoriNumbers/user/configLR " +
                " -w MaoriNumbers/user/wordNetworkNum -o SWT -l '*' -i recout.mlf -p 0.0 -s 5.0" +
                "  MaoriNumbers/user/dictionaryD MaoriNumbers/user/tiedList foo.wav");
    }
}
