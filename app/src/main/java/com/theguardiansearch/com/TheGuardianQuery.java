package com.theguardiansearch.com;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * The AsyncTask class to get data from The Guardian json.
 * @author Lilia Ramalho Martins
 * @version 1.0
 */
class TheGuardianQuery extends AsyncTask<String, Integer, List<TheGuardianArticle>> {

    private List<TheGuardianArticle> articles = new ArrayList<>();
    private URL url;
    private HttpURLConnection urlConnection;
    private InputStream response;
    private ProgressBar progressBar;
    private OnTGQueryCompleted listener;

    /**
     * Constructor with two parameters.
     * @param progressBar ProgressBar that shows the progress while getting the information
     *                    from json.
     * @param listener OnTGQueryCompleted listener that will be called when AsyncTask finishes.
     */
    public TheGuardianQuery (ProgressBar progressBar, OnTGQueryCompleted listener) {
        super();
        this.progressBar = progressBar;
        this.listener = listener;
    }

    /**
     * Overrides method doInBackground from AsyncTask. It establishes a connection with the
     * url in The Guardian site that returns in json format the News searched with a term. The
     * json objects are converted in TheGuardianArticle objects and added to a List that is
     * returned in this method.
     * @param args receive the term to be searched in the first position of the array.
     * @return List of TheGuardianArticles objects filled with data from the json search.
     */
    @Override
    protected List<TheGuardianArticle> doInBackground(String... args) {
        List<TheGuardianArticle> backArticles = new ArrayList<>();
        try {

            String searchUrl = "https://content.guardianapis.com/search?api-key=1fb36b70-1588-4259-b703-2570ea1fac6a&q=" + args[0];
            url = new URL(searchUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            response = urlConnection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            String fullResult = sb.toString();
            JSONObject TGresult = new JSONObject(fullResult);
            JSONObject TGresponse = TGresult.getJSONObject("response");
            JSONArray results = TGresponse.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject anObject = results.getJSONObject(i);
                String id = anObject.getString("id");
                String publicationDate = anObject.getString("webPublicationDate");
                String title = anObject.getString("webTitle");
                String webUrl = anObject.getString("webUrl");
                String sectionName = anObject.getString("sectionName");
                backArticles.add(new TheGuardianArticle(publicationDate, title, webUrl, id, sectionName,false));
                TimeUnit.MILLISECONDS.sleep(50);
                int progress = i*100/results.length();
                publishProgress(progress);
            }
            publishProgress(100);

        } catch (Exception e) {
            Log.e("Error message: ", e.getMessage());
        }

        return backArticles;
    }

    /**
     * Overrides method doProgressUpdate from AsyncTask. It set the progress in the ProgressBar.
     */
    protected void onProgressUpdate(Integer ... args)
    {
        progressBar.setVisibility(View.VISIBLE);
        int progress = args[0];
        progressBar.setProgress(progress);
    }

    /**
     * Overrides method doPostExecute from AsyncTask. When the AsyncTask is finished the method
     * OnQueryCompleted from the OnTGQueryCompleted listener is called passing the List of
     * resulting TheGuardianArticle objects as parameter. The ProgressBar returns to default
     * INVISIBLE state.
     */
    protected void onPostExecute(List<TheGuardianArticle> backArticles)
    {
        for (TheGuardianArticle element : backArticles) {
            articles.add(element);
        }
        listener.onQueryCompleted(articles);
        progressBar.setVisibility(View.INVISIBLE);
    }

}