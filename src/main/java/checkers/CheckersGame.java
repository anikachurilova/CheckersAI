package checkers;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;

public class CheckersGame {
    private String serverUrl = "http://localhost:8081";
    public final String name;
    private minimaxAI minimaxAi;

    private String color;
    private String token;
    private final double  time;
    private volatile boolean isFinished;

    public CheckersGame(String name, minimaxAI minimaxAi,double time) {
        this.minimaxAi = minimaxAi;
        this.name = name;
        this.time = time;
    }

    public void runGame(RestTemplate restT) throws InterruptedException{

        UriComponentsBuilder ur = UriComponentsBuilder.fromUriString(serverUrl + "/game").queryParam("team_name", name);

        ResponseEntity<String> responseFromGame = restT.postForEntity(ur.build().toUri(), null, String.class);

        String temp = responseFromGame.getBody();

        String tk = temp.replace("token", "").replace("RED","").replace("BLACK","")
                .replace("\"","").replace(":","").replace(",","").replace(" ","")
                .replace("success","").replace("status","").replace("data","").replace("color","")
                .replace("{","").replace("}","");

        String cl = temp.replace("token", "").replace(tk,"")
                .replace("\"","").replace(":","").replace(",","").replace(" ","")
                .replace("success","").replace("status","").replace("data","").replace("color","")
                .replace("{","").replace("}","");

        token = tk;
        color = cl;

        minimaxAi.setColor(color);

        int i = 0;
        if(time < 0){
            i++;
            System.out.println(i);
        }
        while (!isFinished) {

            ResponseEntity<Response> getResponseFromGame
                    = restT.getForEntity(serverUrl + "/game", Response.class);
            isFinished =  getResponseFromGame.getBody().getData().isIs_finished();
            System.out.println(getResponseFromGame.getBody());

            Response response = getResponseFromGame.getBody();


            if(isFinished){
                break;
            }

            List<GameBoardPiece> brd = response.getData().getBoard();
            String trn = response.getData().getWhose_turn();

            if (color.equals(trn)) {
                makeStepCh(restT, brd);
            }
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
            }
        }
    }


    public void makeStepCh(RestTemplate restT, List<GameBoardPiece> brd) {
        System.out.println(color + "'s turn");

        System.out.println(minimaxAi.generateStep(boardReal(brd, color)));

        try {
            HttpHeaders hdr = new HttpHeaders();
            hdr.set("Authorization", "Token " + token);
            HttpEntity<Request> requestHttpEntity = new HttpEntity<>(new Request(minimaxAi.generateStep(boardReal(brd, color))), hdr);
            ResponseEntity<String> stringResponseEntity = restT.exchange(serverUrl + "/move", HttpMethod.POST, requestHttpEntity,String.class);
            System.out.println("Response: " + stringResponseEntity);
        } catch (Exception e) {
         //   e.printStackTrace();
            makeStepCh(restT, brd);
        }
    }


    public static int[][] boardReal(List<GameBoardPiece> brd, String clrOfCheck) {
        int[][] result = new int[8][8];

        if(clrOfCheck.equals("BLACK")){
            for (GameBoardPiece gbp : brd) {
                if (gbp.getRow() % 2 != 0) {
                    int counts;
                    String c = gbp.getColor();
                    if(!clrOfCheck.equals(c)){
                        if (gbp.isKing()) {
                            counts = 22;
                        }else{
                            counts = 2;
                        }
                    } else{
                        if (gbp.isKing()) {
                            counts = 11;
                        }else{
                            counts = 1;
                        }

                    }
                    result[gbp.getRow()][gbp.getColumn() * 2 + 1 - 1] = counts;


                } else {
                    int counts;
                    String str = gbp.getColor();
                    if(!clrOfCheck.equals(str)){
                        if (gbp.isKing()) {
                            counts = 22;
                        }else{
                            counts = 2;
                        }
                    } else{
                        if (gbp.isKing()) {
                            counts = 11;
                        }else{
                            counts = 1;
                        }
                    }

                    result[gbp.getRow()][1 + gbp.getColumn() * 2] = counts;
                }
            }
        } else{
            for (GameBoardPiece gbp : brd) {
                if (gbp.getRow() % 2 != 0) {
                    int counts;
                    String str = gbp.getColor();
                    if(!clrOfCheck.equals(str)){
                        if (gbp.isKing()) {
                            counts = 22;
                        }else{
                            counts = 2;
                        }
                    } else{
                        if (gbp.isKing()) {
                            counts = 11;
                        }else{counts = 1;}
                    }
                    result[result.length - 1 - gbp.getRow() ][result.length - gbp.getColumn() * 2 - 1 ] = counts;
                } else {
                    int counts;
                    String c = gbp.getColor();
                    if(!clrOfCheck.equals(c)){
                        if (gbp.isKing()) {
                            counts = 22;
                        }else{
                            counts = 2;
                        }
                    } else{
                        if (gbp.isKing()) {
                            counts = 11;
                        }else {
                            counts = 1;
                        }
                    }
                    result[result.length - 1 - gbp.getRow()][result.length - (gbp.getColumn() * 2 + 1) - 1] = counts;
                }
            }
        }
        return result;
    }
}