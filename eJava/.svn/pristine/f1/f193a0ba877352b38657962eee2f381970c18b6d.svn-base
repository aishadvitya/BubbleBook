package com.iss.bubble.bean;

import com.iss.bubble.ejb.BubbleEJB;
import com.iss.bubble.ejb.DiscussionEJB;
import com.iss.bubble.ejb.UserEJB;
import com.iss.bubble.entity.BubbleBookUser;
import com.iss.bubble.entity.BubbleMap;
import com.iss.bubble.entity.BubbleSurvey;
import com.iss.bubble.entity.Discussion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Test
 */
@Named
@ViewScoped
public class SurveyBubbleBean implements Serializable {
//  private CartesianChartModel categoryModel;  

    private PieChartModel pieModel;
    private int incrementText = 0;
    private ArrayList<String> answers = new ArrayList<String>();
    private String selectedText;
    private BubbleSurvey bubble;
    @EJB
    UserEJB userEJB;
    @EJB
    DiscussionEJB discussionEJB;
    @EJB
    BubbleEJB bubbleEJB;
    private boolean isPersist = false;
    //vinces code 
      private BubbleSurvey persistedEntity;

    public BubbleSurvey getPersistedEntity() {
        return persistedEntity;
    }

    public void setPersistedEntity(BubbleSurvey persistedEntity) {
        this.persistedEntity = persistedEntity;
    }
    
    
    

    public SurveyBubbleBean() {
        bubble = new BubbleSurvey();
    }

    public boolean isIsPersist() {
        return isPersist;
    }

    public void setIsPersist(boolean isPersist) {
        this.isPersist = isPersist;
    }

    public BubbleSurvey getBubble() {
        return bubble;
    }

    public void setBubble(BubbleSurvey bubble) {
        this.bubble = bubble;
    }

    public String getSelectedText() {
        return selectedText;
    }

    public void setSelectedText(String selectedText) {
        this.selectedText = selectedText;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public void addOne() {
        if (bubble.getAnswers().size() > 5) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sorry you cannot add more than 5"));
        } else {

            bubble.getAnswers().add(bubble.getResponse());
            bubble.setResponse("");
            System.out.print(bubble.getAnswers().size());
        }
    }

    public void done() {
       // addOne();

        BubbleBookUser user = userEJB.findUserByID(1);
        Discussion discussion = discussionEJB.findDiscussionByID(1);
        bubble.setUserId(user);
        bubble.setDiscussionId(discussion);
        bubble.setCreateTimestamp(new Date());
        bubble.setBubbleType("SURVEY");
        bubble.setAnswer1(bubble.getAnswers().get(0));

        bubble.setAnswer2(bubble.getAnswers().get(1));
        bubble.setAnswer3((bubble.getAnswers().size() == 3) ? bubble.getAnswers().get(2) : "");
        bubble.setAnswer4((bubble.getAnswers().size() == 4) ? bubble.getAnswers().get(3) : "");
        bubble.setAnswer5((bubble.getAnswers().size() == 5) ? bubble.getAnswers().get(4) : "");

        bubble.setAnswer1Count(0.0);
        bubble.setAnswer2Count(0.0);
       bubbleEJB.saveBubble(bubble,null);
        isPersist = true;
        createPieModel();
    }

    public int getIncrementText() {
        return incrementText;
    }

    public void setIncrementText(int incrementText) {
        this.incrementText = incrementText;
    }

    public PieChartModel getPieModel() {
        return pieModel;
    }

    public void updateResponse(BubbleSurvey bubble) {
       switch (bubble.getResponse()) {

             case ("1"):
                System.out.println("12" + bubble.getAnswer1Count());
                bubble.setAnswer1Count(bubble.getAnswer1Count() + 1);
                break;
             case ("2"):
                bubble.setAnswer2Count(bubble.getAnswer2Count() + 1);
                break;
             case ("3"):
             bubble.setAnswer3Count(bubble.getAnswer3Count() + 1);
             break;
            case ("4"):
             bubble.setAnswer4Count(bubble.getAnswer4Count() + 1);
            break;
            case ("5"):
            bubble.setAnswer5Count(bubble.getAnswer5Count() + 1);
             break;
        }

       bubbleEJB.saveBubble(bubble,null);
    }

    public void createPieModel() {
        if (bubble != null) {
            pieModel = new PieChartModel();
            pieModel.set(bubble.getAnswer1(), bubble.getAnswer1Count());
            pieModel.set(bubble.getAnswer2(), bubble.getAnswer2Count());
            if (bubble.getAnswer3() != null) {
                pieModel.set(bubble.getAnswer3(), bubble.getAnswer3Count());
            }
            if (bubble.getAnswer4() != null) {
                pieModel.set(bubble.getAnswer4(), bubble.getAnswer4Count());
            }
            if (bubble.getAnswer5() != null) {
                pieModel.set(bubble.getAnswer5(), bubble.getAnswer5Count());
            }
        }
    }

}
