package encryptdecrypt;

public class CipherExecutor { //class that takes main execution of Encryption
    private String mode = "enc";
    private int key = 0;
    private String alg = "shift";
    private String data = "";
    private String in = "";
    private String out = "";

    private String inputData = "";


    private DataIn DataIn;
    private DataOut DataOut;
    private CipherAlgorithm CipherAlgorithm;

    CipherExecutor(String[] args) { //constructor that initializing fields and executors
        initializeVariables(args); // converting String[] into fields
        initializeExecutors();  // creating instances of classes based on fields content
    }

    private void initializeExecutors(){ //creating class instance based on fields logic
        DataIn = in.equals("") ? new SimpleDataInput() : new FileDataInput();
        CipherAlgorithm = getAlgorithm();
        DataOut = out.equals("") ? new SimpleDataOutput() : new FileDataOutput();
    }

    private CipherAlgorithm getAlgorithm(){ //returns instance of CipherAlgorithm based on fields logic
        if(mode.equals("enc") && alg.equals("unicode")) {
            return new UnicodeEncode();
        } else if (mode.equals("dec") && alg.equals("unicode")) {
            return new UnicodeDecode();
        } else if (mode.equals("enc") && alg.equals("shift")) {
            return new ShiftEncode();
        } else if (mode.equals("dec") && alg.equals("shift")) {
            return new ShiftDecode();
        }   else {
            throw new RuntimeException("Wrong -mode or -alg");
        }
    }

    public void execute() { // calling execute methods of created classes

        inputData = DataIn.getData(in, data);
        inputData = CipherAlgorithm.executeCipher(inputData, key);
        DataOut.PassData(out, inputData);


    }


    private void initializeVariables (String[] args) {
        for(int i = 0; i < args.length; i += 2) {
            switch (args[i]) {

                case "-mode":
                    mode = args[i + 1];
                    break;

                case "-key":
                    key = Integer.valueOf(args[i + 1]);
                    break;

                case "-data":
                    data = args[i + 1];
                    break;

                case "-in":
                    in = args[i + 1];
                    break;

                case "-out":
                    out = args[i + 1];
                    break;
                case "-alg":
                    alg = args[i + 1];
                    break;
            }
        }
    }
}
