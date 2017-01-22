import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Translator {
 public static final MediaType JSON = MediaType.parse("application/jsonrset=utf-8");
 
 OkHttpClient client = new OkHttpClient();
 String currToken;
 
 
 public Translator() throws IOException {
 this.currToken = requestNewToken();
 //System.out.println(currToken);
 }
 
 public String requestNewToken() throws IOException{
 
 String url = "https://api.cognitive.microsoft.com/sts/v1.0/issueToken" ;
 RequestBody body = RequestBody.create(JSON, "");
 
 //Add your subscription key!!
 Request request = new Request.Builder()
 .url(url)
 .addHeader("Content-Type", "application/json")
 .addHeader("Accept", "application/jwt")
 .addHeader("Ocp-Apim-Subscription-Key", "d1916cbfa86644a2bbc04cf22b9f1bc4")
 .post(body)
 .build();
 
 Response response = client.newCall(request).execute();
 return response.body().string();
 }
 
 public String translate(String textToTranslate, String targetLanguage) throws IOException{
 String url = "https://api.microsofttranslator.com/v2/http.svc/Translate?appid=Bearer " + this.currToken + 
 "&text=" + textToTranslate + "&to=" + targetLanguage;
 
 Request request = new Request.Builder()
 .url(url)
 .addHeader("Accept", "application/xml")
 .build();
 
 Response response = client.newCall(request).execute();
 String s = regex(response.body().string());
 return s;
 }
 
 String regex(String s){
     Pattern p = Pattern.compile("(\\>)(.+)(\\<)");
     Matcher m = p.matcher(s);
     if(m.find()){
        return m.group(2);
     }
     return "";
 }  
 
 /*public static void main(String[] args) throws IOException{
     Translator t = new Translator();
     //System.out.println(t.translate("je mange des pommes", "en"));
     System.out.println(t.translate("आप कैसे हो?", "en"));
     //System.out.println(t.translate("他说", "en"));
 }
 */
}
