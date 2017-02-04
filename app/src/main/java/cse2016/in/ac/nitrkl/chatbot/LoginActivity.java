package cse2016.in.ac.nitrkl.chatbot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by LENOVO on 31-01-2017.
 */
public class LoginActivity extends AppCompatActivity {

    String code,password="fallin";
    EditText enterCode;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String DEFAULT = "N/A";
    Button enterButton;
    DBAdapter2 myDB;

    //table 1 data
    public static String[] areas ={"bba","library","architecture","ncc","basketball","train","watertank","sac","rajendramishra","mainbuilding"};
    //                      ----------------------------------------------------------------------------------------------------------------
    String[] questions ={"These three connected to one International organisation that conducted the art event for which I had got a certificate and a prize. Did you get it?",
                         "Did you get the connect between the three markers? I suppose you did. It’s right there.",
                         "Yes, I remember now. Those people had shot her when she had tried to run away from them. Did you get the gang’s name from the markers?",
                         "I remember the name now. Do you get it? Google can help I guess.",
                         "I remember the context now. These 3 names taken together produced the message Happy Birthday. All you need to do is find me my birth date.",
                         "The three hints connect to one bigger place that has been there as a pop culture reference. Find it asap. We are running out of time.",
                         "Did it read Comet? It surely did. Oh I get it now. It was the year of the most special comet that could have doomed the earth for once and all. And this Great Fire was also one of the biggest catastrophes that year. Get the Comet’s name.",
                         "Link in the answers. The world wide web will do you enough justice.",
                         "Aah, I finally see some way out. Did you get it? The Worli Bridge. Get the exact name.",
                         "There I was featuring in a daily. Headlining it for a reason we wouldn’t want to. Did you get the name?"};
    //----------------------------------------------------------------------------------------------------------------------------
    String[] answers = {"sathyasaiinternationalorganisation",
                        "vaddeyratner",
                        "badbarbies",
                        "khandadharfalls",
                        "june30",
                        "batterseapowerstation",
                        "halleyscomet",
                        "youtube",
                        "bandraworlisealink",
                        "punemirror"};
    //------------------------------------------------------------------------------------------------------------------------
    String[] stories = {"This was my first ever prize in any competition ever organised. And I was overwhelmed by it. Thank you for reminding me one of my best memories of childhood. Let’s move ahead for now.",
                        "So here is the author of the book that I used to read. The book’s name was “In the shadow of the Banyan.” Something based on Civil War as I faintly remember. Keep the author’s name and book name too, it will lead to a part of the final key. Let’s move to the next area.",
                        "Bad Barbies was a notorious female gang who were known for many murders. They tell me that Barbie was not the one they had come for on the incident day. Rather it was me. They abducted the wrong girl and were at fault for not getting their work done. This is why I don’t like this memory of mine as it haunted me throughout my childhood. Enough here. Let’s move faster. Time is running out.",
                        "This was a family outing. All of us had been to this place for a picnic and I was mesmerised by the sheer beauty of this place. Waterfall, cold winds, winter and family. An experience worth a lifetime. Let’s move to the next area for now.",
                        "Aah, I finally remember my birth date. How relieved I am? I at least have my birth identity. That will make things easier for you from now on. Let’s move forward.",
                        "I had seen this place once in a movie and also in Pink Floyd’s album. Pink Floyd was one of my favourite bands and their success story is worth knowing. Keep the name in mind, it will be used later.",
                        "I have been attracted to the stories of things that are high up in the sky and Halley’s Comet is one of them. 1910 was the year when Halley’s Comet brushed its tail just past the Earth. Had it been a little more closer, Earth would have been a history now. Move fast or else I will be in the history books forever.",
                        "YouTube was a sharing platform where people came together and shared things. There was this YouTube Youth Fest. People from different spheres of life were selected to showcase their ideas for the betterment of this place. I had got selected for the same and it was supposed to be held at Pune that year.",
                        "Finally, a link that will help me to get out. A sea link it is. I remember this very well. A trip it was with my friends. A trip bound to be remembered a lifetime. A getaway from the monotonous life and an exhilarating experience. We are close, close enough. We have crossed over the bridge. Let’s cross over the final frontier now.",
                        "Was this supposed to end like this? I don't really know. I was supposed to get out of it. But I couldn't. The reason why I am trapped here and I think my diary needed a final ending. That's the reason we are all here."
    };

    //----------------------------------------------------------------------------------------------------------------------------------------------


//    table 2 data
    public static String[] areas2 ={"bba","library","architecture","ncc","basketball","train","watertank","sac","rajendramishra","mainbuilding"};
    String[] questions2 ={"I faintly remember something. This is where this beautiful journey started. This place has captured many memories, and it will continue to do so. Lucky is the first concrete when it was laid for its foundation. Even though I don’t know the person who laid it, I have heard many a stories about him. Fondly he used to be called by his designation. I am not exactly remembering what it was. Help me to it please.",
                        "Yes, the Principal. I see you found a certificate here. Since my childhood, I was fond of solving puzzles and riddles. In fact I got many prizes in numerous competitions. But there was this one riddle that I got stuck on and couldn’t connect to the place. It holds the question to the next clue. Will you help me solve it? Let me show you the puzzle.    It flows: __ __ __ __ __\n" +
                                "    A train won’t start without it: __ __ __ __ __ __\n" +
                                "    This fest: __ __ __ __ __ __ __ __ __\n" +
                                "    Boys follow: __ __ __ __ __\n" +
                                "    It is burnt when you exercise: __ __ __ __ __ __ __ __\n" +
                                "   Need a cab? Download this app: __ __ __\n" +
                                "    __ __ __ __ __ __ And Gentlemen\n" +
                                "Did you get it? Try harder please. It’s right there in the answers. The only vague memory I have of this place is a message written on one of the walls of it. Enter this place and relate the message written on the first wall you face with someone.\n",
                        "It’s a beautiful painting indeed. I remember, when I was a child, painting, drawing was one of my favorite hobbies. Water colors, crayons and color pencils were my best friends. I remember this area somewhat. There were people working with paints. There were ladders. They were painting a wall with different patterns and with different colors. Can you see it? I found one word difficult to read as it was not aligned in the same way as others. Can you read it?Translate it into hindi and give the answer",
                        "I remember walking through a busy stretch. People from different walks of life rushing through the lane. Quotes lined up that stretch of road. Quotes, which actually reflect a lot about our lives. I have learnt a lot from them while growing although I am not able to recollect all of them now. Some of them were like this.\n" +
                                "\n" +
                                " “God has given us one face and we make ourselves another. We should never judge by the harvest but by the seeds we reap, for victory belongs to the most preserving. We live in a wonderful world that is full of beauty, charm and adventure, which we can seek with our eyes open.”\n" +
                                "There was another quote with good work and bad end. I don’t exactly remember where it was written. Can you please help me find it?\n",
                        "Yes, “No one who does good work will ever come to a bad end, either here or there”. I remember now. There was a huge banyan tree there, beside this quote. I have never been interested in holy books. Fantasy eluded me. When I was this little kid, I had a book on Black Magic. I had stumbled upon it somewhere and had kept it with me since then. I used to read it secretly. Fear getting caught, I used to hide this book someplace else. Oh God! I just can’t remember which place was it. It’s getting difficult now. Wait. I remember a portrait of a man outside that building. Not so big, not so small. Fully in white. I remember my mother narrating an incident about him that he was a pilot and he was termed as a “Lion”. May be you can help me out by searching for the room adjacent to his portrait.",
                        "Oh! Thank you. Reading hall it was. Let me go and check for the book. My favourite book. It’s not here. May be its stolen or may be its lost. But wait. I remember sending it to someone through mail. The entire book. I can’t remember the name of the book even. The name and the entire book have to be there in one of my sent folders. Can you help me find the email address from my Sent folder so that my lost book can be retrieved? ",
                        "There used to be a trail of images here. I along with my best friend used to come here all the time and look at the trail of images that sprang up as we crossed it. The reflections moved with us and we used to keep running by and forth along it smiling and laughing. I am not exactly remembering my friend’s name. She had the same name and birth date as one of the most sought after things by little girls. Maybe this can help you.",
                        "Aah, yes. Barbie. Her name was Barbie. And she shared her date of birth with the same day when Barbie was launched. What a coincidence it was. It’s getting to me now why I have a bad memory of this place. Something happened here. We were playing hide and seek when two persons came out from behind a green box and pulled her along and were gone. They were reprimanded for it and they were sentenced to prison. But that was the last time, I had seen Barbie. Their names are written somewhere along the green box as I remember. Please find it for me.",
                        "I didn’t know until a long time what exactly had happened. I was told that she had gone off to a different place until I read a newspaper cutting which happened to lay somewhere. Trudging along the way and taking a right from the tree leads me to it as far as I remember. Written there is a cipher that tells me how was my friend murdered. Help me to it please.",
                        "Strewn amongst the trees are memories of mine which I hold close. Carved in their trunks are memoirs of a lifetime. Happy and joyful, cheerful and content. All of them stacked together in this area. I remember having a  cipher carved in the trees. Rearrange it for me to decode it.",
                        "I remember this now. This is my favourite game. Anagrams. I so love them. Another thing that I love most, are Translations. Hindi was a big deal for me. So I had used the word Skeleton as a translation to a word that was hard for me to remember. Lead me to a place where the hands raised up holding the balls decipher the next part of it. The number of balls equate to the number of letters in the translated word. Interchange the last two letters and wait for me to remember the next part of it.",
                        "There was some Ancient goddess named Kankala Devi who consumed trees, soil and everything else. I had heard this folklore from my grandma. I had once visited the place that had this folklore but I don’t remember its name now. Maybe I have a clue to find out its distance from here.  The number of palm trees lining up the area is a cipher to it. Inserting a zero in between gives the distance.",
                        "My friends and I used to come here almost every evening. 23 gold is a huge achievement. Truly a great inspiration. I am forgetting the exact place. Outside the place, there is a beautiful pattern of flowers with a tree in the centre. Are you sure you are there? You aren’t cheating right? Ok. Let me check. Can you count the number of flower vases in the pattern?",
                        "So you are there. Michael Phelps it was. What a phenomenal character he is. 23 might be his lucky number. Mine is 16, coz its sweet. But when you add 1 to it, it becomes a bit unlucky for someone. 17 vs 14. One aching for 18 and the other willing for 15. Five years is a long wait. Last Sunday history was created. Not created, it was repeated. I guess you have spotted what I am talking about. If yes, then rush to that spot and you will find a code scribbled in the centre. And I hope you know unity addition.",
                        "So that it was. Roger Federer, my favourite player. Another person that I loved most had a saying, “I just want to conquer people and their souls.” Truly positive in this world today. The age of the person will help me a lot in this regard.",
                        "The best journeys ever taken are always in trains. Only medium of transport which people from all spheres of life can afford. Once I had been on a trip to a place where there were different kinds of trains on display. A train museum you can say. It was pretty. The marker number of this is same as that of the addition of the spokes in bigger and smaller wheels of our train. ",
                        "Coal engine was the first of its kind and trains used to run on them. I was enchanted by the smoke coming out of the train and the sound it made. Once my father had taken me to a place somewhere here where they had a lab that taught about the combustion of these things and the process related to it. Please find it for me.",
                        "That was the Fuel and Combustion Lab there. I was scared of fire then. So I somehow made an excuse and came out of the building. A pretty sight attracted me. The colors were really beautiful there. One of the prettiest sights that my eyes had ever behold. I am not exactly remembering its color. Do you see it?",
                        "I remember, I used to cling on my father’s back and he would climb to a very high spot. So high that, I would close my eyes as I was too afraid of heights. That spot rested on pillars and my father would ask me to count the pillars and by the time I would end my counting, we would reach the top. The view from the top! People would look like ants. Once my friends climbed it and one of them wrote his name on the wall. He wrote it so big, that it’s still visible from a long way from the ground. I can’t recollect who wrote it. Can you check it please?",
                        "Yes,I remember now. It was Amlan. How can I forget! There was a beautiful building which was visible from the top. It was a maze by itself. It had ramps, lifts, stairs. I remember it was still under construction somewhere around 2009. The day of the inauguration of the building, in 2010, my father asked me if I would go to the ceremony as a respected politician was to inaugurate it. But it was Amlan’s birthday that day. And I had to attend his b’day party. Oh god! I forgot his birthday! Can you help me find it? Answer format: Month date",
                        "Oh, this date. It was his birthday. But why do the markers show Fire? Something must have happened on this date some year. It must be a significant year too. It must be a Great Fire. Can you please find out which year was it?",
                        "When I was a kid, I used to climb the steps and rush down again. The steps were synonymous with a cipher hidden in my diary. To unlock it, I need a number that only the steps can provide.",
                        "Aah, the 14th of February I remember now. It was one of the red-letter days in history. Naah, not for the reason you think. I am not talking about Valentine’s Day. Valentine’s is a long way back. This happened in recent history. Right some years after the start of this millennium. Walk along the road to the place where the bells tinker and flames flicker. The tinkering bells will provide you the clue.",
                        "Perfect. That’s the date I was talking about. Right at the stroke of some hour, something came up that is a sensation now. Walk some steps back to the place where water doesn’t quench the thirst nor fill the tank. Decode the text in alphanumeric and let me know.\n" +
                                "Secret text: IAC 16 13\n",
                        "Thanks for recalling his name. He was one of the pioneers for the set up of this place as you see it today. He had always been the harbinger of new opportunities and choices. Going to its door step and viewing in an all round manner, you fight fall upon something new too.",
                        "This marker is a hint to the other location as far as I remember. Lead me to it and we shall try to decipher the next part of it.",
                        "We are getting closer. An entry into the world of tech finally. There must be something here that can help us to find the key as soon as possible. Jai Maa Bhadrakali might be of help to you this time. Locate it asap.",
                        "Mirror Mirror on the wall, ask the right question for once and all.",
                        "The next stop is MC- 127. The car trip I was talking about. We were there 6 friends on a trip to Pune. There was a high traffic on the road. So we thought of taking an alternate way to it. So what we did was we took an alternate high speed road. It’s a different name to a highway.",
                        "Take me to the room where Highway and Concrete are linked together. So there we were making merry, having fun when life took a screeching halt and things came to a standstill. Can you tell me what exactly happened? Am I dead or Alive?"};
    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------
    String[] answers2 = {"principal", "saibaba", "gyaan",
                        "bhagwadgita","readinghall","thisweek@sac.nitr",
                        "barbie","cumminsjakson","gun",
                        "skeleton","kankala","104",
                        "24","deuce","50",
                        "22","420","pink",
                        "amlanchandan","august21","1910",
                        "14","5","913pm",
                        "newchoice","jd","worli",
                        "amismart","expressway","dead"};
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    String[] finalAns2 = {"acertificate","godpainting","knowledge",
                        "banyantree","library","thebookofshadows",
                        "doll","murderer","marie",
                        "skeleton","goddess","waterfall",
                        "phelps","federer","miketyson",
                        "coal","fire","floyd",
                        "firebrigade","agnee","comets",
                        "february","2005","www",
                        "housefull2","mobile","bridge",
                        "mirror","highway","deadbody"
    };
    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    String[] blno2 = {"Turn to Marker 5","Turn to Marker 29","Turn to Marker 23",
                    "Turn to Marker 21","Turn to Marker 8","Turn to Marker 9",
                    "Turn to Marker 26","Turn to Marker 12","Turn to Marker 1",
                    "Turn to Marker 3","Turn to Marker 20","Turn to Marker 16",
                    "Turn to Marker 11","Turn to Marker 10","Turn to Marker 27",
                    "Turn to Marker 22","Turn to Marker 6","Turn to Marker 7",
                    "Turn to Marker 15","Turn to Marker 19","Turn to Marker 14",
                    "Turn to Marker 4","Turn to Marker 13","Turn to Marker 17",
                    "Turn to Marker 2","Turn to Marker 25","Turn to Marker 30",
                    "Turn to Marker 18","Turn to Marker 24","Turn to Marker 28"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        myDB = new DBAdapter2(this);
        sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String uname = sharedPreferences.getString("name", DEFAULT);
        if (uname.equals(password)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        enterButton = (Button)findViewById(R.id.enterButton);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterCode= (EditText) findViewById(R.id.enter_game);
                code = enterCode.getText().toString();
                if(code.equals(password)) {
                    editor.putString("name", code);
                    editor.apply();
                    fillDatabase();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    enterCode.setText("");
                    Toast.makeText(getApplicationContext(),"Wrong code",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void fillDatabase() {
        myDB.open();
        for (int i=0;i<10;i++) {
            long l = myDB.insertRow(i,areas[i], questions[i], answers[i], stories[i]);
        }

        for (int i=0;i<30;i++) {
            long l = myDB.insertRow2(i, areas2[i / 3], (i % 3) + 1, questions2[i], answers2[i], blno2[i], finalAns2[i]);
            Log.i("log",l+"");
        }

        myDB.close();
    }

}
