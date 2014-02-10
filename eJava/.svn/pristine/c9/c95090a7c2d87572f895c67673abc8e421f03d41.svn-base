/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iss.bubble.bean;

import com.iss.bubble.ejb.UserEJB;
import com.iss.bubble.entity.BubbleBookUser;
import com.iss.bubble.util.BubbleUtil;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.Principal;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import javax.swing.ImageIcon;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Sathish
 */
@ManagedBean
@ViewScoped
public class LoginBean implements Serializable {

    @EJB
    UserEJB userEJB;

    public LoginBean() {
        bubbleBookUser = new BubbleBookUser();
    }
    private BubbleBookUser bubbleBookUser;
    private String username;
    //@Size(min = 1, max = 100)
    private String password;
    //@Size(min = 1, max = 100)
    private String email;
    private String aboutme;
    private ImageIcon imageIcon;
    private boolean isNewUser = false;
    private UploadedFile file;
    private InputStream imagefile;
    private String imageUrl = "NONE";
    private String workingDir;
    private String role;
    Properties prop;
    private Principal userPrincipal;
    private ExternalContext facesExternalContext;
    private boolean userAuthenticated;
    private String remomteUsername;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAboutme() {
        return aboutme;
    }

    public void setAboutme(String aboutme) {
        this.aboutme = aboutme;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
        System.out.println("FILE " + file.getFileName());
    }

    public boolean isIsNewUser() {
        return isNewUser;
    }

    public void setIsNewUser(boolean isNewUser) {
        this.isNewUser = isNewUser;
    }

    public String getUsername() {
        return username;
    }

    public BubbleBookUser getBubbleBookUser() {
        return bubbleBookUser;
    }

    public void setBubbleBookUser(BubbleBookUser bubbleBookUser) {
        this.bubbleBookUser = bubbleBookUser;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String validateUser() {
        return "";
    }

    public boolean isLoggedUserIn() {
        facesExternalContext = FacesContext.getCurrentInstance().getExternalContext();
        userPrincipal = facesExternalContext.getUserPrincipal();
        System.out.println("userPrincipal++" + userPrincipal);
        if (userPrincipal != null) {
            remomteUsername = facesExternalContext.getRemoteUser();
            System.out.println("check:" + remomteUsername);
            System.out.println("checked Role:" + facesExternalContext.isUserInRole("USER"));
            return true;
        }
        return false;
    }

    public boolean isUserAuthenticated() {
        System.out.println("inside in--:" + userPrincipal.getName());
        if (isLoggedUserIn()) {
            System.out.println("logged in:" + userPrincipal.getName());
            return true;
        } else {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(
                        FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/faces/error.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(DiscussionBean.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                return false;
            }
        }
    }

    public void setUserAuthenticated(boolean iuserAuthenticated) {
        this.userAuthenticated = userAuthenticated;
    }

    public String logout() {
        System.out.println("Logging out user ---> ");
        HttpSession session = BubbleUtil.getSession();
        session.invalidate();
        return "/welcome?faces-redirect=true";
    }

    public String login() {

        System.out.println("login " + this.email);
        BubbleBookUser user = userEJB.isAuthenticated(this.email, this.password);
        System.out.println("Authenticate user: " + user + " image: " + user.getImageUrl());

        FacesContext context = FacesContext.getCurrentInstance();
        if (user == null) {
            FacesMessage error = new FacesMessage("Invalid email or password. Try again.");
            context.addMessage(":loginForm:password_error", error);
            return null;
        } else {
            //go to home page.
            Map<String, Object> sessionMap = context.getExternalContext().getSessionMap();
            sessionMap.put("bubblebookuser", user);
            System.out.println("Login: User in session: " + sessionMap.get("bubblebookuser").toString());
            return ("home?faces-redirect=true");
        }

    }

    public void clearValues() {
        username = "";
        password = "";
        email = "";
        aboutme = "";
        imageUrl = "";
    }

    public void showRegister(ActionEvent actionEvent) {
        isNewUser = true;
        System.out.println("showRegister " + isNewUser);
    }

    //unused. try for pajax
    public void checkEmail() {
        System.out.println("In check mail-->");
        if (userEJB.emailExists(this.email)) {
            System.out.println("Check mail: Email already exists! Cannot register.");
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage msg = new FacesMessage();
            msg.setSummary("Email id already exists!");
            context.addMessage(null, msg);
        }
    }

    public boolean hasEmail() {
        return userEJB.emailExists(this.email);
    }

    public String modifyProfile() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, Object> sessionMap = context.getExternalContext().getSessionMap();
        BubbleBookUser user = (BubbleBookUser) sessionMap.get("bubblebookuser");
        System.out.println("Original Profile: User in session: " + user);
        if (!this.aboutme.equals("")) {
            System.out.println("About " + this.aboutme);
            user.setAbtMe(this.aboutme);
        }
        if (!this.username.equals("")) {
            System.out.println("Name " + this.username);
            user.setName(this.username);
        }
        if (this.file != null) {
            System.out.println("File " + this.file.getFileName());
            user.setImageUrl(this.file.getFileName());
        }
        System.out.println("Modified Profile: User in session: " + user);

        Boolean success = userEJB.updateBookUser(user);

        if (success) {
            System.out.println("Merge user successful " + user);
            sessionMap.put("bubblebookuser", user);
            System.out.println("Update Success: " + success);
            FacesMessage msg = new FacesMessage("");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            clearValues();
            return null;
//            return ("home?faces-redirect=true");
        } else {
            //check
            System.out.println("Update failed: " + success);
            FacesMessage msg = new FacesMessage("Update failed");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }
    }

    public String saveUser() {

        if (hasEmail()) {
            System.out.println("Email already exists! Cannot register.");
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage error = new FacesMessage("Email already exists!");
            context.addMessage(":registerForm:registerPanel:email", error);
            return null;
        } else {

            System.out.println(bubbleBookUser.getName());
            System.out.println("saveUser " + isNewUser);
            System.out.println("file " + file);
            System.out.println("email " + email);

            bubbleBookUser.setAbtMe(aboutme);
            bubbleBookUser.setPassword(password);
            bubbleBookUser.setEmail(email);
            bubbleBookUser.setName(username);

            if (imageUrl.equals("NONE")) {
                imageUrl = "BubbleLogo.gif";
            }
            bubbleBookUser.setImageUrl(imageUrl);
            bubbleBookUser.setLastLogonTime(new Date());

            Boolean success = userEJB.saveBookUser(bubbleBookUser, role);

            if (success) {
                System.out.println("Successful Registration: " + success);
                // goto home page.
                FacesContext context = FacesContext.getCurrentInstance();
                Map<String, Object> sessionMap = context.getExternalContext().getSessionMap();
                BubbleBookUser user = userEJB.findUserByEmail(this.email);
                sessionMap.put("bubblebookuser", user);

                System.out.println("Register: User in session: " + sessionMap.get("bubblebookuser").toString());
                FacesMessage msg = new FacesMessage("Registration Success");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                //return ("/welcome?faces-redirect=true");
                return null;
            } else {
                System.out.println("Registration failed: " + success);
                FacesMessage msg = new FacesMessage("UnSuccesful");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return null;
            }
        }
    }

    public StreamedContent getImageFile() {
        StreamedContent content = null;
        try {
            if (this.file != null) {
                content = new DefaultStreamedContent(file.getInputstream(), file.getFileName());
            }
        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return content;
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {

            System.out.println("YO  " + event.getFile());
            file = event.getFile();
            System.out.println("FO  " + file.getFileName());

            workingDir = System.getProperty("user.dir") + "\\temp";
            System.out.println("Working dir " + workingDir);

            prop = new Properties();
            InputStream input
                    = getClass().getResourceAsStream("/com/iss/bubble/resources/messages.properties");
            prop.load(input);
            workingDir = prop.getProperty("uploadpath");
            System.out.println("Upload dir from config file " + workingDir);

            File targetFolder = new File(workingDir);
            if (!targetFolder.exists()) {
                System.out.println("Target folder :" + targetFolder + " Success? " + targetFolder.mkdirs());
            }

            InputStream inputStream = event.getFile().getInputstream();
            OutputStream out = new FileOutputStream(new File(targetFolder,
                    event.getFile().getFileName()));
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            inputStream.close();
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            imagefile = event.getFile().getInputstream();
            file = event.getFile();
            //bImageFromConvert = ImageIO.read(imagefile);
            //imageUrl = workingDir + event.getFile().getFileName();
            imageUrl = event.getFile().getFileName();

            BufferedImage image = ImageIO.read(imagefile);
            byte[] bytes = IOUtils.toByteArray(imagefile);
            System.out.println("THe handlg blyte array " + bytes);
            //Blob blob = new SerialBlob(bytes);

            imageIcon = new ImageIcon(image);

        } catch (Exception ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        FacesMessage msg = new FacesMessage("Succesful: " + event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

}
