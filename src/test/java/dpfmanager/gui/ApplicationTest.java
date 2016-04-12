package dpfmanager.gui;

import dpfmanager.shell.modules.report.core.ReportGenerator;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import org.apache.commons.lang.SystemUtils;
import org.controlsfx.control.spreadsheet.SpreadsheetView;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.testfx.api.FxRobot;
import org.testfx.api.FxRobotException;
import org.testfx.api.FxToolkit;
import org.testfx.toolkit.ApplicationFixture;

import java.awt.*;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Adrià Llorens on 30/12/2015.
 */
public abstract class ApplicationTest extends FxRobot implements ApplicationFixture {

  //Set properties for headless mode (Windows only)
  static {
    if (SystemUtils.IS_OS_WINDOWS) {
//      System.setProperty("testfx.robot", "glass");
//      System.setProperty("testfx.headless", "true");
    }
  }

  /**
   * The constant width.
   */
  final static int width = 970;
  /**
   * The constant height.
   */
  final static int height = 500;
  /**
   * The Base w.
   */
  static int baseW = 0;
  /**
   * The Base h.
   */
  static int baseH = 0;

  /**
   * The Max timeout.
   */
  static int maxTimeout = 120;

  /**
   * The Stage.
   */
  static Stage stage;
  /**
   * The Scene.
   */
  protected Scene scene;
  /**
   * The View.
   */
  static SpreadsheetView view;
  private int scroll = 0;

  /**
   * Launch stage.
   *
   * @param appClass the app class
   * @param appArgs  the app args
   * @return the stage
   * @throws Exception the exception
   */
  public static Stage launch(Class<? extends Application> appClass, String... appArgs) throws Exception {
    stage = FxToolkit.registerPrimaryStage();
    FxToolkit.setupApplication(appClass, appArgs);

    //Set the base width and height
    Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
    baseW = (int) primaryScreenBounds.getMinX();
    baseH = (int) primaryScreenBounds.getMinY();

    //Custom size
    stage.setWidth(width);
    stage.setHeight(height);
    stage.setX(baseW);
    stage.setY(baseH);

    // Wait for application to start
    Thread.sleep(2000);
    return stage;
  }

  /**
   * Internal before.
   *
   * @throws Exception the exception
   */
  @Before
  public final void internalBefore() throws Exception {
    // Initial, set log level to severe (remove JacpFX logs)
    Logger rootLog = Logger.getLogger("");
    rootLog.setLevel(Level.SEVERE);

    FxToolkit.setupApplication(this);
  }

  /**
   * Internal after.
   *
   * @throws Exception the exception
   */
  @After
  public final void internalAfter() throws Exception {
    FxToolkit.cleanupStages();
    FxToolkit.cleanupApplication(this);
  }

  @Override
  public void init() throws Exception {
  }

  @Override
  public void start(Stage stage) throws Exception {
    FxToolkit.showStage();
  }

  @Override
  public void stop() throws Exception {
    FxToolkit.hideStage();
  }

  /**
   * Click on and reload.
   *
   * @param id the id
   */
  public void clickOnAndReload(String id){
    clickOnAndReload(id, 250);
  }

  /**
   * Click on and reload top.
   *
   * @param id the id
   */
  public void clickOnAndReloadTop(String id){
    clickOnAndReloadTop(id, 250);
  }

  /**
   * Click on and reload.
   *
   * @param id     the id
   * @param search the search
   */
//Main click function + wait for node + reload
  public void clickOnAndReload(String id, String search){
    // Click first
    clickOnScroll(id);

    // Reload until node search is visible
    reloadScene();
    Node node = scene.lookup(search);
    int count = 0;
    while (node == null && count < maxTimeout *4){
      sleep(250);
      count++;
      reloadScene();
      node = scene.lookup(search);
    }
    sleep(250);
  }

  /**
   * Click on and reload.
   *
   * @param id    the id
   * @param milis the milis
   */
//Main click function + wait + reload
  public void clickOnAndReload(String id, int milis){
    clickOnScroll(id);
    sleep(milis);
    reloadScene();
  }

  /**
   * Click on and reload top.
   *
   * @param id     the id
   * @param search the search
   */
//Main click function + wait for node + reload (top pane)
  public void clickOnAndReloadTop(String id, String search){
    // Click first
    clickOnScroll(id, true, true);

    // Reload until node search is visible
    reloadScene();
    Node node = scene.lookup(search);
    int count = 0;
    while (node == null && count < maxTimeout *4){
      sleep(250);
      count++;
      reloadScene();
      node = scene.lookup(search);
    }
    sleep(250);
  }

  /**
   * Wait until exists.
   *
   * @param id     the id
   */
  public void waitUntilExists(String id) {
    reloadScene();
    Node node = scene.lookup(id);
    int count = 0;
    while (node == null && count < maxTimeout *4){
      sleep(250);
      count++;
      reloadScene();
      node = scene.lookup(id);
    }
  }

  /**
   * Wait until exists.
   *
   * @param id     the id
   */
  public void waitForTable(String id) {
    reloadScene();
    Node node = scene.lookup(id);
    int count = 0;
    while ((node == null || ((TableView)node).getItems().size() == 0) && count < maxTimeout *4){
      sleep(250);
      count++;
      reloadScene();
      node = scene.lookup(id);
    }
  }

  /**
   * Click on and reload top.
   *
   * @param id    the id
   * @param milis the milis
   */
//Main click function + wait + reload (top pane)
  public void clickOnAndReloadTop(String id, int milis){
    clickOnScroll(id, true, true);
    sleep(milis);
    reloadScene();
  }

  /**
   * Click on scroll application test.
   *
   * @param id the id
   * @return the application test
   * @throws FxRobotException the fx robot exception
   */
  public ApplicationTest clickOnScroll(String id) throws FxRobotException {
    return clickOnScroll(id,true, false);
  }

  /**
   * Click on scroll application test.
   *
   * @param id      the id
   * @param restart the restart
   * @return the application test
   * @throws FxRobotException the fx robot exception
   */
  public ApplicationTest clickOnScroll(String id, boolean restart) throws FxRobotException {
    return clickOnScroll(id,restart, false);
  }

  /**
   * Click on scroll application test.
   *
   * @param id       the id
   * @param restart  the restart
   * @param topItems the top items
   * @return the application test
   * @throws FxRobotException the fx robot exception
   */
  public ApplicationTest clickOnScroll(String id, boolean restart, boolean topItems ) throws FxRobotException {
    // First go to the button and decide if we need scroll or not
    if (!moveToCustom(id)){
      // We need scroll
      boolean ret = false;
      int maxScroll = 150;
      if (restart) {
        restartScroll();
      }
      while (!ret && scroll < maxScroll) {
        makeScroll(10,false);
        ret = moveToCustom(id);
      }
      if (scroll == maxScroll){
        throw new FxRobotException("Node "+id+" not found! Scroll timeout!");
      }
    }

    // Now check if we are under top bar
    int y = getMousePositionY();
    if (y < baseH+50 && !topItems) {
      restartScroll();
      return clickOnScroll(id, false, topItems);
    }

    // Now we can move to the button, lets check if it is at bounds of scene
    int minH = height + baseH -25;
    if (minH < y){
      // We are at limit, so make one scroll more
      makeScroll(1,true);
    }

    // Finally we can click the button
    clickOnCustom(id);
    scene = stage.getScene();
    return this;
  }

  /**
   * Click on scroll funcional application test.
   *
   * @param id       the id
   * @param restart  the restart
   * @param topItems the top items
   * @return the application test
   * @throws FxRobotException the fx robot exception
   */
  public ApplicationTest clickOnScrollFUNCIONAL(String id, boolean restart, boolean topItems ) throws FxRobotException {
    // Check if the node if at limit. Position < height + base Height - 5
    // If it is, make one scroll and finish
    if (moveToCustom(id)){
      int y = getMousePositionY();
      int minH = height + baseH -5;
      // Check limits
      if (minH < y){
        makeScroll(1,true);
      }
      // Check under top bar
      if (y < 50 && !topItems) {
        restartScroll();
        restart = false;
      }
    }

    //Click without scroll
    boolean ret = clickOnCustom(id);
    if (ret){
      scene = stage.getScene();
      return this;
    }

    // Else Scroll
    int maxScroll = 150;
    if (restart) {
      restartScroll();
    }
    while (!ret && scroll < maxScroll) {
      makeScroll(10,false);
      ret = checkAndClick(id);
    }
    if (scroll == maxScroll){
      throw new FxRobotException("Node "+id+" not found! Scroll timeout!");
    }
    return this;
  }

  //Try to click checking bounds
  private boolean checkAndClick(String id){
    if (moveToCustom(id)){
      int y = getMousePositionY();
      int minH = height + baseH -5;
      // Check limits
      if (minH < y){
        makeScroll(1,true);
      }
      return clickOnCustom(id);
    }
    return false;
  }

  private void restartScroll() {
    moveTo(100 + baseW, 100 + baseH);
    if (scroll > 0){ //Return to initial scroll
      robotContext().getScrollRobot().scrollUp(scroll);
      scroll = 0;
    }
  }

  private void makeScroll(int x, boolean move){
    if (move) {
      moveTo(100 + baseW, 100 + baseH);
    }
    scroll = scroll + x;
    robotContext().getScrollRobot().scrollDown(scroll);
  }

  private boolean clickOnCustom(String id) {
    try {
      clickOn(id);
      return true;
    } catch (FxRobotException ex) {
      if (ex.getMessage().contains("but no nodes were visible")) {
        return false;
      }
      throw ex;
    }
  }

  private boolean moveToCustom(String id) {
    try {
      moveTo(id);
      return true;
    } catch (FxRobotException ex) {
      if (ex.getMessage().contains("but no nodes were visible")) {
        return false;
      }
      throw ex;
    }
  }

  /**
   * Reload scene.
   */
  public void reloadScene() {
    scene = stage.getScene();
  }

  /**
   * Write text.
   *
   * @param id   the id
   * @param text the text
   */
  protected void writeText(String id, String text) {
    TextField txtField = (TextField) scene.lookup(id);
    txtField.clear();
    txtField.setText(text);
  }

  /**
   * Wait for check files.
   */
  protected void waitForCheckFiles() {
    sleep(1000);
    int timeout = 0;

    // Wait processing pane
    boolean finish = false;
    while (!finish && timeout < maxTimeout) {
      reloadScene();
      Node node = scene.lookup("#loadingVbox");
      if (node != null) {
        timeout++;
        sleep(1000);
      } else {
        finish = true;
      }
    }

    // Wait load report
    finish = false;
    while (!finish && timeout < maxTimeout) {
      reloadScene();
      Node node = scene.lookup("#butDessign");
      if (node == null) {
        timeout++;
        sleep(1000);
      } else {
        finish = true;
      }
    }

    Assert.assertNotEquals("Check files reached timeout! (" + maxTimeout + "s)", maxTimeout, timeout);
  }

  private int getMousePositionY(){
    Point point = MouseInfo.getPointerInfo().getLocation();
    return (int) point.getY();
  }

  private int getMousePositionX(){
    Point point = MouseInfo.getPointerInfo().getLocation();
    return (int) point.getX();
  }

  /**
   * Get current reports int.
   *
   * @return the int
   */
  protected int getCurrentReports(){
    int nReports = 0;
    String path = ReportGenerator.getReportsFolder();
    File reports = new File(path);

    File[] dates = reports.listFiles();
    if (dates == null || dates.length == 0){
      return 0;
    }
    for (File date : dates){
      if (date.isDirectory()){
        File[] ids = date.listFiles();
        nReports = nReports + ids.length;
      }
    }
    return nReports;
  }

  /**
   * Click on imported config.
   *
   * @param path the path
   */
  protected void clickOnImportedConfig(String path) {
    VBox vbox = (VBox) scene.lookup("#vBoxConfig");                         //Get VBox
    ScrollPane scrollPane = (ScrollPane) scene.lookup("#configScroll");   //Get ScrollPane
    String idToClick = "#";
    String search = path.replaceAll("/", "_").replaceAll("\\\\", "_");
    for (Node node : vbox.getChildren()) {
      RadioButton rb = (RadioButton) node;
      String text = rb.getText().replaceAll("/", "_").replaceAll("\\\\", "_");
      if (text.endsWith(search)) {
        idToClick += rb.getId();
      }
    }
    Assert.assertNotEquals("Import config file failed!", "#", idToClick);

    // Move inside configuration pane
    moveTo("#vBoxConfig");

    // Check button in limit
    moveTo(idToClick);
    int limitY = (int) (scrollPane.localToScreen(scrollPane.getBoundsInLocal()).getMinY() + scrollPane.getHeight());
    int currentY = getMousePositionY();
    if (currentY > limitY-2){
      // Move inside configuration pane and scroll down
      moveTo("#vBoxConfig");
      makeScroll(1, false);
    }

    // Now click and scroll
    clickOnScroll(idToClick,false);
  }
}
