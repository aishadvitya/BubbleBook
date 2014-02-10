/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iss.bubble.bean;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.iss.bubble.ejb.BubbleEJB;
import com.iss.bubble.ejb.DiscussionEJB;
import com.iss.bubble.ejb.UserEJB;
import com.iss.bubble.entity.Bubble;
import com.iss.bubble.entity.BubbleAV;
import com.iss.bubble.entity.BubbleBookUser;
import com.iss.bubble.entity.BubbleEvent;
import com.iss.bubble.entity.BubbleHierarchy;
import com.iss.bubble.entity.BubbleMap;
import com.iss.bubble.entity.BubbleSurvey;
import com.iss.bubble.entity.Discussion;
import com.iss.bubble.entity.GroupTable;
import com.iss.bubble.util.BubbleUtil;
import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

@Named
@SessionScoped
public class DiscussionBean implements Serializable {

    @EJB
    BubbleEJB bubbleejb;
    @EJB
    DiscussionEJB discEjb;
    @EJB
    UserEJB userEjb;
    @Inject
    ContactsBean contactsBean;

    private Bubble newBubble;
    private Bubble newReplyBubble;
    private Bubble parent;
    private BubbleMap mapBubble;
    private BubbleSurvey surveyBubble;
    //vinces video
    private BubbleAV AVBubble;
    //vinces Events
    private BubbleEvent eventBubble;
    //Bubble Variables
    private TreeNode root;
    private TreeNode selectedNode;
    private String bubblesSrc;
    private DiscussionDataModel discussionsModel;
    private ParticipantDiscussionDataModel participantDiscussionsModel;
    private Discussion selectedDiscussion;
    private Bubble selectedBubble = null;
    private boolean discSelected = false;
    private boolean editSelected = false;
    private Principal userPrincipal;
    private ExternalContext facesExternalContext;
    private String username;
    TreeNode rootForFilter;
    private Date searchDate;
    private String bubbleDisplay;
    private boolean isNew = true;

    private List<Bubble> lstBubbles;
    private List<Discussion> discussions;
    private List<Discussion> participantDiscussions;
    private List<Bubble> bubbles;
    ArrayList<TreeNode> treeNodes = new ArrayList<>();
    private List<Bubble> lstByDate;
    ArrayList<TreeNode> treeByFilter = new ArrayList<>();
    List<String> roles = new ArrayList<>();
    List<BubbleBookUser> selectedSearchParticipants = new ArrayList<BubbleBookUser>();
    List<BubbleBookUser> searchParticipants = new ArrayList<BubbleBookUser>();

    public DiscussionBean() {
    }

    @PostConstruct
    public void initDiscs() {
        //Get User from Session
        if (isUserAuthenticated()) {
            putUserinSession(userEjb.findUserByEmail(username));
        } else {
            try {
                facesExternalContext.redirect(
                        FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/faces/error.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(DiscussionBean.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                return;
            }
        }
        BubbleBookUser bbUser = BubbleUtil.getCurrentUserFromSession();
        if (bbUser == null) {
            try {
                facesExternalContext.redirect(
                        FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/faces/welcome.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(DiscussionBean.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                return;
            }
        }
        List<GroupTable> userGroups = userEjb.getGroupByUserEmail(bbUser.getEmail());
        if (userGroups.size() < 1) {
            try {
                facesExternalContext.redirect(
                        FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/faces/error.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(DiscussionBean.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                return;
            }
        }

        for (GroupTable gt : bbUser.getGroupTableCollection()) {
            roles.add(gt.getGroupTablePK().getGroupName());
        }
        if (roles.contains("ADMIN")) {
            buildAllDiscussionsTable();
        } else {
            buildParticipantDiscussionsTable(bbUser);
            buildMyDiscussionsTable(bbUser);
        }
        discSelected = true;
        bubblesSrc = "/bubbles.xhtml";
    }

    //Admin Display
    private void buildAllDiscussionsTable() {
        participantDiscussions = new ArrayList<>();
        participantDiscussions = discEjb.retrieveAllDiscussions();
        participantDiscussionsModel = new ParticipantDiscussionDataModel(participantDiscussions);
        selectedDiscussion = new Discussion();
        if (participantDiscussions != null) {
            if (participantDiscussions.size() > 0) {
                selectedDiscussion = participantDiscussions.get(0);
                buildBubbleTree(participantDiscussions.get(0).getDiscussionId());
            }
        }
    }

    //Changes for search Criteria starts
    private void buildParticipantDiscussionsTable(BubbleBookUser bbUser) {
        //Participant Discussions
        participantDiscussions = new ArrayList<>();
        participantDiscussions = discEjb.retrieveParticipantDiscussionsByUsersID(bbUser);
        participantDiscussionsModel = new ParticipantDiscussionDataModel(participantDiscussions);
        selectedDiscussion = new Discussion();
        if (participantDiscussions != null) {
            if (participantDiscussions.size() > 0) {
                selectedDiscussion = participantDiscussions.get(0);
                buildBubbleTree(participantDiscussions.get(0).getDiscussionId());
            }
        }
    }

    private boolean isUserAuthenticated() {
        facesExternalContext = FacesContext.getCurrentInstance().getExternalContext();
        userPrincipal = facesExternalContext.getUserPrincipal();
        if (userPrincipal != null) {
            username = userPrincipal.getName();
            System.out.println("check:" + username);
            System.out.println("checked Role:" + facesExternalContext.isUserInRole("USER"));
            return true;
        }
        return false;
    }

    private void putUserinSession(BubbleBookUser user) {
        Map<String, Object> sessionMap = facesExternalContext.getSessionMap();
        sessionMap.put("bubblebookuser", user);
    }

    public void buildMyDiscussionsTable(BubbleBookUser bbUser) {
        //My Discussions
        discussions = new ArrayList<>();
        discussions = discEjb.retrieveDiscussionsByUsersID(bbUser);
        discussionsModel = new DiscussionDataModel(discussions);
        if (discussions != null) {
            if (discussions.size() > 0) {
                selectedDiscussion = discussions.get(0);
                buildBubbleTree(discussions.get(0).getDiscussionId());
            }
        }
    }

    public ArrayList<TreeNode> buildTreeFull(List<Bubble> listOfBubbles, ArrayList<TreeNode> tn, TreeNode root) {
        Map<Bubble, List<Bubble>> hm = new TreeMap<>(new MyComparator());
        if (listOfBubbles != null) {
            for (Bubble bu : listOfBubbles) {
                if (null == bu.getBubbleHierarchy() || null == bu.getBubbleHierarchy().getParentBubble() || null == bu.getBubbleHierarchy().getParentBubble().getBubbleId()) {
                    List<BubbleHierarchy> lst = (List<BubbleHierarchy>) bu.getBubbleHierarchyCollection();
                    List<Bubble> bub = new ArrayList<>();
                    for (BubbleHierarchy l : lst) {
                        bub.add(l.getBubble());
                    }
                    hm.put(bu, bub);
                }
            }
        }
        for (Bubble b : hm.keySet()) {
            if (hm.get(b) != null) {
                Collections.sort(hm.get(b), new MyComparator());
            }
            tn = addToParent(b, root);
        }
        return tn;
    }

    public void buildBubbleTree(Integer discussionId) {
        root = new DefaultTreeNode("root", null);
        lstBubbles = new ArrayList<>();
        lstBubbles = (List<Bubble>) bubbleejb.retrieveBubbles(discussionId);
        treeNodes = buildTreeFull(lstBubbles, treeNodes, root);
    }

    // public void filterByDateAndName(List<String> names, Timestamp ts) {
    public void filterByDateAndName() {
        List<String> names = new ArrayList<>();
        for (BubbleBookUser user : selectedSearchParticipants) {
            names.add(user.getName());
        }
        if (selectedSearchParticipants.isEmpty() && null != searchDate) {
            filterByDate();
            return;
        }
        if (null == searchDate && !selectedSearchParticipants.isEmpty()) {
            filterByNames(names);
            return;
        }
        if (!searchParticipants.isEmpty() && null != searchDate) {

            lstByDate = new ArrayList<>();
            rootForFilter = new DefaultTreeNode("root", null);
            for (Bubble b : lstBubbles) {
                if ((b.getCreateTimestamp().before(searchDate) || b.getCreateTimestamp().equals(searchDate)) && (names.contains(b.getUserId().getName()))) {
                    lstByDate.add(b);
                }
            }
            treeByFilter = buildTreeFull(lstByDate, treeByFilter, rootForFilter);
            return;
        }
    }

    public void filterByNames(List<String> names) {
        treeByFilter = new ArrayList<>();
        lstByDate = new ArrayList<>();
        rootForFilter = new DefaultTreeNode("root", null);
        for (Bubble b : lstBubbles) {
            if (names.contains(b.getUserId().getName())) {
                lstByDate.add(b);
            }
        }
        treeByFilter = buildTreeFull(lstByDate, treeByFilter, rootForFilter);
    }

    public void filterByDate() {
        lstByDate = new ArrayList<>();
        rootForFilter = new DefaultTreeNode("root", null);
        for (Bubble b : lstBubbles) {
            if ((b.getCreateTimestamp().before(searchDate) || b.getCreateTimestamp().equals(searchDate))) {
                lstByDate.add(b);
            }
        }
        buildTreeFull(lstByDate, treeByFilter, rootForFilter);
    }

    public ArrayList<TreeNode> addToParent(Bubble h, TreeNode parent) {
        TreeNode temp = new DefaultTreeNode(h, parent);
        treeNodes.add(temp);
        if (h.getBubbleHierarchyCollection().isEmpty()) {
            return treeNodes;
        } else {
            h.setBubbleHierarchyCollection(sortReplies((List<BubbleHierarchy>) h.getBubbleHierarchyCollection()));
            for (BubbleHierarchy bh : h.getBubbleHierarchyCollection()) {
                addToParent(bh.getBubble(), temp);
            }
            return treeNodes;
        }
    }

    public void showNewDiscussion() {
        selectedDiscussion = new Discussion();
        contactsBean.clearSelectedContacts();
        contactsBean.fetchMyContacts(BubbleUtil.getCurrentUserFromSession());
        bubblesSrc = "/newDiscussion.xhtml";
    }

    public void createNewDiscussion() {
        selectedDiscussion.setUser(BubbleUtil.getCurrentUserFromSession());
        selectedDiscussion.setCreateTimestamp(new Date());
        saveParticipantsToDiscussion();
        discEjb.saveDiscussion(selectedDiscussion);
        if (roles.contains("ADMIN")) {
            buildAllDiscussionsTable();
        } else {
            buildMyDiscussionsTable(BubbleUtil.getCurrentUserFromSession());
        }
        bubblesSrc = "/bubbles.xhtml";
    }

    public void cancelNewDiscussion() {
        selectedDiscussion = new Discussion();
        contactsBean.clearSelectedContacts();
        discSelected = false;
        bubblesSrc = "/bubbles.xhtml";
    }

    public void saveParticipantsToDiscussion() {
        if (selectedDiscussion.getDiscussionId() == null) {
            selectedDiscussion.setBubbleBookUserCollection(contactsBean.getSelectedContacts());
        } else {
            discEjb.addUsersToDiscussion(contactsBean.getSelectedContacts(), selectedDiscussion);
        }
    }

    public void onRowToggle(ToggleEvent event) {
        //Fetch the Paticipants Images
        //on expansion
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Discussion view",
                "Title:" + ((Discussion) event.getData()).getTitle());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowSelect(SelectEvent event) {
        //based on the discussion Id
        // Fetch the Bubbles
        buildBubbleTree(selectedDiscussion.getDiscussionId());
        searchParticipants.clear();
        searchParticipants = (List<BubbleBookUser>) selectedDiscussion.getBubbleBookUserCollection();
        searchParticipants.add(selectedDiscussion.getUser());
        bubblesSrc = "/bubbles.xhtml";
        FacesMessage msg = new FacesMessage("Discussion Selected", ((Discussion) event.getObject()).getTitle());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        selectedSearchParticipants = null;
        discSelected = false;
        FacesMessage msg = new FacesMessage("Discussion Unselected", ((Discussion) event.getObject()).getTitle());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    //Edit Bubble 1
    public void editBubble(Bubble b) {
        if (!editSelected) {
            editSelected = true;
            setSelectedBubble(b);
            selectedBubble.setIsEditable(true);
        }
    }

    //Edit Bubble 2
    public void saveEditedBubble(Bubble b) {
        setSelectedBubble(b);
        selectedBubble.setIsEditable(false);
        bubbleejb.saveBubble(b, null);
        editSelected = false;
    }

    //Edit Bubble 3
    public void CancelEdit(Bubble b) {
        selectedBubble.setIsEditable(false);
        editSelected = false;
    }

    //Creating a new bubble
    public void createNewBubble(String type) {
        if (type.equalsIgnoreCase("TEXT")) {
            newBubble = new Bubble();
        } else if (type.equalsIgnoreCase("MAP")) {
            mapBubble = new BubbleMap(1.2941205501556396, 103.78105926513672, "NUS,Singapore");
            newBubble = mapBubble;
        } else if (type.equalsIgnoreCase("SURVEY")) {
            surveyBubble = new BubbleSurvey();
            newBubble = surveyBubble;
        } else if (type.equalsIgnoreCase("AV")) {
            AVBubble = new BubbleAV();
            newBubble = AVBubble;
        } else if (type.equalsIgnoreCase("EVENT")) {
            eventBubble = new BubbleEvent();
            newBubble = eventBubble;
        }
        newBubble.setBubbleType(type);
        newBubble.setCreateTimestamp(new Date());
        lstBubbles.add(newBubble);
        root.getChildren().add(0, new DefaultTreeNode(newBubble, root));
    }

    public void replyNewBubble(String type) {
        isNew = false;
        if (type.equalsIgnoreCase("TEXT")) {
            newBubble = new Bubble();
        } else if (type.equalsIgnoreCase("MAP")) {
            mapBubble = new BubbleMap(1.2941205501556396, 103.78105926513672, "NUS,Singapore");
            newBubble = mapBubble;
        } else if (type.equalsIgnoreCase("SURVEY")) {
            surveyBubble = new BubbleSurvey();
            newBubble = surveyBubble;
        } else if (type.equalsIgnoreCase("AV")) {
            AVBubble = new BubbleAV();
            newBubble = AVBubble;
        } else if (type.equalsIgnoreCase("EVENT")) {
            eventBubble = new BubbleEvent();
            newBubble = eventBubble;
        }
        newBubble.setBubbleType(type);
        newBubble.setCreateTimestamp(new Date());
        parent = (Bubble) selectedNode.getData();
        selectedNode.setExpanded(true);
        treeNodes.add(new DefaultTreeNode(newBubble, selectedNode));
    }

    public void cancelBubble() {
        lstBubbles.remove((Bubble) root.getChildren().get(0).getData());
        root.getChildren().remove(0);
    }

    public void createBubble() {
        newBubble.setCreateTimestamp(new Date());
        newBubble.setDiscussionId(selectedDiscussion);
        newBubble.setUserId(BubbleUtil.getCurrentUserFromSession());
        if (isNew) {
            bubbleejb.saveBubble(newBubble, null);
        } else {
            bubbleejb.saveBubble(newBubble, parent);
            isNew = true;
        }
        buildBubbleTree(selectedDiscussion.getDiscussionId());
        if (selectedNode != null) {
            selectedNode.setExpanded(true);
        }
    }

    public void updateMap() {
        if (mapBubble == null) {
            mapBubble = new BubbleMap(1.2941205501556396, 103.78105926513672, "NUS,Singapore");
        }
        final Geocoder geocoder = new Geocoder();
        LatLng coord2 = new LatLng(1.2941205501556396, 103.78105926513672);
        List<GeocoderResult> results = new ArrayList<GeocoderResult>();
        GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(mapBubble.getPlace()).setLanguage("en").getGeocoderRequest();
        GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
        if (geocoderResponse != null) {
            results = geocoderResponse.getResults();
            if (!results.isEmpty()) {
                coord2 = new LatLng(results.get(0).getGeometry().getLocation().getLat().floatValue(), results.get(0).getGeometry().getLocation().getLng().floatValue());
            }
        }
        mapBubble.setLocationLat(coord2.getLat());
        mapBubble.setLocationLon(coord2.getLng());
        newBubble = mapBubble;
    }

    public MapModel getSimpleMapModel(Double lat, Double lng, String place) {
        LatLng coord1 = new LatLng(lat, lng);
        Marker marker = new Marker(coord1, place);
        MapModel simpleModel = new DefaultMapModel();
        simpleModel.addOverlay(marker);
        return simpleModel;
    }

    public void addMarker(PointSelectEvent actionEvent) {
        mapBubble.getSimpleModel().getMarkers().clear();
        Marker marker = new Marker(actionEvent.getLatLng(), mapBubble.getPlace());
        mapBubble.getSimpleModel().addOverlay(marker);
        mapBubble.setLocationLat(actionEvent.getLatLng().getLat());
        mapBubble.setLocationLon(actionEvent.getLatLng().getLng());
    }

    //survey 
    public void updateResponse(BubbleSurvey bubble) {
        switch (bubble.getResponse()) {
            case ("1"):
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
        newBubble = bubble;
        parent = null;
        createBubble();
    }

    //display survey 
    public void addOne() {
        if (surveyBubble.getAnswers().size() >= 2) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sorry you cannot add more than 5"));
        } else {
            surveyBubble.getAnswers().add(surveyBubble.getResponse());
            surveyBubble.setResponse("");
        }
        newBubble = surveyBubble;
    }

    public void done() {
        surveyBubble.setAnswer1Count(0.0);
        surveyBubble.setAnswer2Count(0.0);
        surveyBubble.setAnswer3Count(0.0);
        surveyBubble.setAnswer4Count(0.0);
        surveyBubble.setAnswer5Count(0.0);
        newBubble = surveyBubble;
        createBubble();
    }

    //video codes
    public String UrlLink(String url) {
        String urlLink = "";
        if (url.contains("v=")) {
            String vid = url.split("v=")[1];
            urlLink = "http://www.youtube.com/v/" + vid;
        } else {
            urlLink = url;
        }
        return urlLink;
    }

    //Event Codes
    public void respond(BubbleEvent be) {
        if (be.getResponse().equals("1")) {
            be.setGoingCount(be.getGoingCount() + 1);
        }
        if (be.getResponse().equals("2")) {
            be.setNotgoingCount(be.getNotgoingCount() + 1);
        }
        if (be.getResponse().equals("3")) {
            be.setMaybeCount(be.getMaybeCount() + 1);
        }
        newBubble = be;
        parent = null;
        createBubble();
    }

    public List<BubbleHierarchy> sortReplies(List<BubbleHierarchy> bubbles) {
        Collections.sort(bubbles, new MyComparator2());
        return bubbles;
    }

    public TreeNode getRootForFilter() {
        return rootForFilter;
    }

    public void setRootForFilter(TreeNode rootForFilter) {
        this.rootForFilter = rootForFilter;
    }

    public Date getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(Date searchDate) {
        this.searchDate = searchDate;
    }

    public List<BubbleBookUser> getSearchParticipants() {
        return searchParticipants;
    }

    public void setSearchParticipants(List<BubbleBookUser> searchParticipants) {
        this.searchParticipants = searchParticipants;
    }

    public List<BubbleBookUser> getSelectedSearchParticipants() {
        return selectedSearchParticipants;
    }

    public void setSelectedSearchParticipants(List<BubbleBookUser> selectedSearchParticipants) {
        this.selectedSearchParticipants = selectedSearchParticipants;
    }
    //end

    public String getBubbleDisplay() {
        return bubbleDisplay;
    }

    public boolean isIsNew() {
        return isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public boolean isDiscSelected() {
        return discSelected;
    }

    public void setDiscSelected(boolean discSelected) {
        this.discSelected = discSelected;
    }

    public BubbleEvent getEventBubble() {
        return eventBubble;
    }

    public void setEventBubble(BubbleEvent EventBubble) {
        this.eventBubble = EventBubble;
    }

    //vince events
    public BubbleAV getAVBubble() {
        return AVBubble;
    }

    public void setAVBubble(BubbleAV AVBubble) {
        this.AVBubble = AVBubble;
    }

    public BubbleSurvey getSurveyBubble() {
        return surveyBubble;
    }

    public void setSurveyBubble(BubbleSurvey surveyBubble) {
        this.surveyBubble = surveyBubble;
    }

    public BubbleMap getMapBubble() {
        return mapBubble;
    }

    public void setMapBubble(BubbleMap mapBubble) {
        this.mapBubble = mapBubble;
    }

    public Bubble getParent() {
        return parent;
    }

    public void setParent(Bubble parent) {
        this.parent = parent;
    }

    public Bubble getNewReplyBubble() {
        return newReplyBubble;
    }

    public void setNewReplyBubble(Bubble newReplyBubble) {
        this.newReplyBubble = newReplyBubble;
    }

    public Bubble getNewBubble() {
        return newBubble;
    }

    public void setNewBubble(Bubble newBubble) {
        this.newBubble = newBubble;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public DiscussionDataModel getDiscussionsModel() {
        return discussionsModel;
    }

    public void setDiscussionsModel(DiscussionDataModel discussionsModel) {
        this.discussionsModel = discussionsModel;
    }

    public List<Bubble> getBubbles() {
        return bubbles;
    }

    public void setBubbles(List<Bubble> bubbles) {
        this.bubbles = bubbles;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public Discussion getSelectedDiscussion() {
        return selectedDiscussion;
    }

    public void setSelectedDiscussion(Discussion selectedDiscussion) {
        this.selectedDiscussion = selectedDiscussion;
    }

    public String getBubblesSrc() {
        return bubblesSrc;
    }

    public Bubble getSelectedBubble() {
        return selectedBubble;
    }

    public void setSelectedBubble(Bubble selectedBubble) {
        this.selectedBubble = selectedBubble;
    }

    public void setBubblesSrc(String bubblesSrc) {
        this.bubblesSrc = bubblesSrc;
    }

    public ParticipantDiscussionDataModel getParticipantDiscussionsModel() {
        return participantDiscussionsModel;
    }

    public void setParticipantDiscussionsModel(ParticipantDiscussionDataModel participantDiscussionsModel) {
        this.participantDiscussionsModel = participantDiscussionsModel;
    }

    public List<Discussion> getDiscussions() {
        return discussions;
    }

    public void setDiscussions(List<Discussion> discussions) {
        this.discussions = discussions;
    }

    public List<Discussion> getParticipantDiscussions() {
        return participantDiscussions;
    }

    public void setParticipantDiscussions(List<Discussion> participantDiscussions) {
        this.participantDiscussions = participantDiscussions;
    }
}
