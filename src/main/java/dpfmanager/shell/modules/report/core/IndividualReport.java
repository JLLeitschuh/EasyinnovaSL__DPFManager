/**
 * <h1>ReportGenerator.java</h1>
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version; or, at your choice, under the terms of the
 * Mozilla Public License, v. 2.0. SPDX GPL-3.0+ or MPL-2.0+.
 * </p>
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License and the Mozilla Public License for more details.
 * </p>
 * <p>
 * You should have received a copy of the GNU General Public License and the Mozilla Public License
 * along with this program. If not, see <a
 * href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a> and at <a
 * href="http://mozilla.org/MPL/2.0">http://mozilla.org/MPL/2.0</a> .
 * </p>
 * <p>
 * NB: for the © statement, include Easy Innova SL or other company/Person contributing the code.
 * </p>
 * <p>
 * © 2015 Easy Innova, SL
 * </p>
 *
 * @author Adrià Llorens Martinez
 * @version 1.0
 * @since 23/6/2015
 */

package dpfmanager.shell.modules.report.core;

import dpfmanager.conformancechecker.tiff.implementation_checker.Validator;
import dpfmanager.conformancechecker.tiff.implementation_checker.rules.RuleResult;

import com.easyinnova.tiff.model.TiffDocument;
import com.easyinnova.tiff.model.types.IFD;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class IndividualReport.
 */
public class IndividualReport implements Comparable {

  /** The file name. */
  private String filename;

  /** The file path. */
  private String filepath;

  /** The real file name. */
  private String reportFilename;

  /** The file path. */
  private String reportpath;

  /** The baseline errors list. */
  private List<RuleResult> errorsBl;

  /** The baseline warning list. */
  private List<RuleResult> warningsBl;

  /** The Tiff EP errors list. */
  private List<RuleResult> errorsEp;

  /** The Tiff EP warning list. */
  private List<RuleResult> warningsEp;

  /** The Tiff IT errors list. */
  private List<RuleResult> errorsIt0;

  /** The Tiff IT warning list. */
  private List<RuleResult> warningsIt0;

  /** The Tiff IT errors list. */
  private List<RuleResult> errorsIt1;

  /** The Tiff IT warning list. */
  private List<RuleResult> warningsIt1;

  /** The Tiff IT errors list. */
  private List<RuleResult> errorsIt2;

  /** The Tiff IT warning list. */
  private List<RuleResult> warningsIt2;

  /** The Tiff PC errors list. */
  private List<RuleResult> errorsPc;

  /** The Tiff PC warning list. */
  private List<RuleResult> warningsPc;

  /** The Tiff Document object. */
  private TiffDocument tiffModel;

  /**
   * Check Tiff/IT conformance.
   */
  public boolean checkIT0;

  /**
   * Check Tiff/IT conformance.
   */
  public boolean checkIT1;

  /**
   * Check Tiff/IT conformance.
   */
  public boolean checkIT2;

  /**
   * Check Tiff/EP conformance.
   */
  public boolean checkEP;

  /**
   * Check Baseline conformance.
   */
  public boolean checkBL;

  private ArrayList<RuleResult> pcValidation;

  /**
   * Check Policy.
   */
  public boolean checkPC;

  private IndividualReport compareIr;

  private String document;

  private PDDocument pdf;

  private boolean containsData;

  private String conformanceCheckerReport = null;

  private String conformanceCheckerReportHtml = null;

  private String conformanceCheckerReportMets = null;

  /**
   * Extra check information
   */
  private String internalReportFodler;

  private int idReport;

  private long uuid;

  private boolean error;

  /**
   * Error constructor
   */
  public IndividualReport(boolean e) {
    error = e;
  }

  /**
   * Constructor + generate.
   *
   * @param name               the name
   * @param path               the path
   * @param reportFilename               the path
   */
  public IndividualReport(String name, String path, String reportFilename) {
    filename = name;
    filepath = path;
    this.reportFilename = reportFilename;
    containsData = false;
  }

  public boolean isError() {
    return error;
  }

  public void setInternalReportFolder(String internal){
    internalReportFodler = internal;
  }

  public String getInternalReportFodler() {
    return internalReportFodler;
  }

  public int getIdReport() {
    return idReport;
  }

  public void setIdReport(int idReport) {
    this.idReport = idReport;
  }

  public long getUuid() {
    return uuid;
  }

  public void setUuid(long uuid) {
    this.uuid = uuid;
  }

  public void setConformanceCheckerReport(String report) {
    conformanceCheckerReport = report;
  }

  public void setConformanceCheckerReportHtml(String report) {
    conformanceCheckerReportHtml = report;
  }

  public void setConformanceCheckerReportMets(String report) {
    conformanceCheckerReportMets = report;
  }

  public String getConformanceCheckerReport() {
    return conformanceCheckerReport;
  }

  public String getConformanceCheckerReportHtml() {
    return conformanceCheckerReportHtml;
  }

  public String getConformanceCheckerReportMets() {
    return conformanceCheckerReportMets;
  }

  public boolean containsData() {
    return containsData;
  }

  /**
   * Constructor + generate.
   *
   * @param name               the name
   * @param path               the path
   * @param tiffModel          the TIFF model
   * @param baselineValidation the baseline validation
   * @param epValidation       the EP validation
   * @param itValidation       the IT validation
   */
  public IndividualReport(String name, String path, String rFilename, TiffDocument tiffModel,
                          Validator baselineValidation, Validator epValidation, Validator itValidation, Validator it1Validation, Validator it2Validation) {
    filename = name;
    filepath = path;
    containsData = true;
    reportFilename = rFilename;
    generate(tiffModel, baselineValidation, epValidation, itValidation, it1Validation, it2Validation);
  }

  /**
   * Sets pc validation.
   *
   * @param pcValidation the pc validation
   */
  public void setPcValidation(ArrayList<RuleResult> pcValidation) {
    this.pcValidation = pcValidation;
    processPcValidation();
  }

  /**
   * Gets pc validation.
   *
   * @return the pc validation
   */
  public List<RuleResult> getPcValidation() {
    return pcValidation;
  }

  /**
   * Sets report path.
   *
   * @param path the path
   */
  public void setReportPath(String path) {
    reportpath = path;
  }

  /**
   * Gets report path.
   *
   * @return the report path
   */
  public String getReportPath() {
    return reportpath;
  }

  /**
   * Set file name.
   *
   * @param name the new file name
   */
  public void setFileName(String name) {
    filename = name;
  }

  /**
   * Get report file name.
   *
   * @return reportFilename
   */
  public String getReportFileName() {
    return reportFilename;
  }

  /**
   * Get file name.
   *
   * @return filename file name
   */
  public String getFileName() {
    return filename;
  }

  /**
   * Sets compare report.
   *
   * @param ir the ir
   */
  public void setCompareReport(IndividualReport ir) {
    compareIr = ir;
  }

  /**
   * Gets compare report.
   *
   * @return the compare report
   */
  public IndividualReport getCompareReport() {
    return compareIr;
  }

  /**
   * Sets pdf document.
   *
   * @param document the document
   */
  public void setPDFDocument(String document) {
    this.document = document;
  }

  public void setPDF(PDDocument doc) {
    this.pdf = doc;
  }

  public PDDocument getPDF() {
    return pdf;
  }

  /**
   * Gets pdf document.
   *
   * @return the pdf document
   */
  public String getPDFDocument() {
    return document;
  }

  /**
   * Get file path.
   *
   * @return filepath file path
   */
  public String getFilePath() {
    return filepath;
  }

  /**
   * Set file path.
   *
   * @param path the new file name
   */
  public void setFilePath(String path) {
    filepath = path;
  }

  /**
   * Calculate percent.
   *
   * @return the int
   */
  public int calculatePercent() {
    Double rest = 100.0;
    IndividualReport ir = this;
    if (ir.checkEP && ir.hasEpValidation()) rest -= ir.getEPErrors().size() * 12.5;
    if ((ir.checkIT0 || ir.checkIT1 || ir.checkIT2) && ir.hasItValidation()) {
      int n0 = ir.getITErrors(0).size();
      int n1 = ir.getITErrors(1).size();
      int n2 = ir.getITErrors(2).size();
      if (!ir.checkIT0) n0 = 0;
      if (!ir.checkIT1) n1 = 0;
      if (!ir.checkIT2) n2 = 0;
      rest -= (n0 + n1 + n2) * 12.5;
    }
    if (ir.checkBL && ir.hasBlValidation()) rest -= ir.getBaselineErrors().size() * 12.5;
    if (ir.checkPC && ir.hasPcValidation()) rest -= ir.getPCErrors().size() * 12.5;
    if (rest < 0.0) {
      rest = 0.0;
    }
    return rest.intValue();
  }

  /**
   * Generate the report information.
   *
   * @param tiffModel    the tiff model
   * @param validation   the baseline validation
   * @param epValidation the EP validation
   * @param it0Validation the IT validation
   * @param it1Validation the IT-1 validation
   * @param it2Validation the IT-2 validation
   */
  public void generate(TiffDocument tiffModel, Validator validation,
                       Validator epValidation, Validator it0Validation, Validator it1Validation, Validator it2Validation) {
    this.tiffModel = tiffModel;

    // errors & warnings
    if (validation != null) {
      errorsBl = validation.getErrors();
      warningsBl = validation.getWarnings();
    }
    if (epValidation != null) {
      errorsEp = epValidation.getErrors();
      warningsEp = epValidation.getWarnings();
    }
    if (it0Validation != null) {
      errorsIt0 = it0Validation.getErrors();
      warningsIt0 = it0Validation.getWarnings();
    }
    if (it1Validation != null) {
      errorsIt1 = it1Validation.getErrors();
      warningsIt1 = it1Validation.getWarnings();
    }
    if (it2Validation != null) {
      errorsIt2 = it2Validation.getErrors();
      warningsIt2 = it2Validation.getWarnings();
    }
    if (pcValidation != null) {
      processPcValidation();
    }
  }

  void processPcValidation() {
    errorsPc = new ArrayList<>();
    warningsPc = new ArrayList<>();
    for (RuleResult rr : pcValidation) if (!rr.getWarning()) errorsPc.add(rr);
    for (RuleResult rr : pcValidation) if (rr.getWarning()) warningsPc.add(rr);
  }

  /**
   * Has pc validation boolean.
   *
   * @return the boolean
   */
  public boolean hasPcValidation(){
    return errorsPc != null;
  }

  /**
   * Has bl validation boolean.
   *
   * @return the boolean
   */
  public boolean hasBlValidation(){
    return errorsBl != null;
  }

  /**
   * Has ep validation boolean.
   *
   * @return the boolean
   */
  public boolean hasEpValidation(){
    return errorsEp != null;
  }

  /**
   * Has it validation boolean.
   *
   * @return the boolean
   */
  public boolean hasItValidation(){
    return errorsIt0 != null || errorsIt1 != null || errorsIt2 != null;
  }

  /**
   * Has it validation boolean.
   *
   * @return the boolean
   */
  public boolean hasItValidation(int profile){
    if (profile == 0) return errorsIt0 != null;
    if (profile == 1) return errorsIt1 != null;
    return errorsIt2 != null;
  }

  /**
   * Get errors list.
   *
   * @return the errors
   */
  public List<RuleResult> getBaselineErrors() {
    if (errorsBl == null) return new ArrayList<RuleResult>();
    return errorsBl;
  }

  /**
   * Get warnings list.
   *
   * @return the warnings
   */
  public List<RuleResult> getBaselineWarnings() {
    if (warningsBl == null) return new ArrayList<RuleResult>();
    return warningsBl;
  }

  /**
   * Get EP errors list.
   *
   * @return the errors
   */
  public List<RuleResult> getEPErrors() {
    if (errorsEp == null) return new ArrayList<RuleResult>();
    return errorsEp;
  }

  /**
   * Get EP warnings list.
   *
   * @return the warnings
   */
  public List<RuleResult> getEPWarnings() {
    if (warningsEp == null) return new ArrayList<RuleResult>();
    return warningsEp;
  }

  /**
   * Get IT errors list.
   *
   * @return the errors
   */
  public List<RuleResult> getITErrors(int profile) {
    if (profile == 0) {
      if (errorsIt0 == null) return new ArrayList<RuleResult>();
      return errorsIt0;
    } else if (profile == 1) {
      if (errorsIt1 == null) return new ArrayList<RuleResult>();
      return errorsIt1;
    } else {
      if (errorsIt2 == null) return new ArrayList<RuleResult>();
      return errorsIt2;
    }
  }

  /**
   * Get IT warnings list.
   *
   * @return the warnings
   */
  public List<RuleResult> getITWarnings(int profile) {
    if (profile == 0) {
      if (warningsIt0 == null) return new ArrayList<RuleResult>();
      return warningsIt0;
    } else if (profile == 1) {
      if (warningsIt1 == null) return new ArrayList<RuleResult>();
      return warningsIt1;
    } else {
      if (warningsIt2 == null) return new ArrayList<RuleResult>();
      return warningsIt2;
    }
  }

  /**
   * Get PC errors list.
   *
   * @return the errors
   */
  public List<RuleResult> getPCErrors() {
    if (errorsPc == null) return new ArrayList<RuleResult>();
    return errorsPc;
  }

  /**
   * Get PC warnings list.
   *
   * @return the warnings
   */
  public List<RuleResult> getPCWarnings() {
    if (warningsPc == null) return new ArrayList<RuleResult>();
    return warningsPc;
  }

  /**
   * Get Tiff Model.
   *
   * @return the tiffModel
   */
  public TiffDocument getTiffModel() {
    return tiffModel;
  }

  /**
   * Sets tiff model.
   *
   * @param model the model
   */
  public void setTiffModel(TiffDocument model) {
    tiffModel = model;
  }

  /**
   * Gets n ep err.
   *
   * @return the n ep err
   */
  public int getNEpErr() {
    return getEPErrors() == null ? 0 : getEPErrors().size();
  }

  /**
   * Gets n bl err.
   *
   * @return the n bl err
   */
  public int getNBlErr() {
    return getBaselineErrors() == null ? 0 : getBaselineErrors().size();
  }

  /**
   * Gets n it err.
   *
   * @return the n it err
   */
  public int getNItErr(int profile) {
    return getITErrors(profile) == null ? 0 : getITErrors(profile).size();
  }

  /**
   * Gets n ep war.
   *
   * @return the n ep war
   */
  public int getNEpWar() {
    return getEPWarnings() == null ? 0 : getEPWarnings().size();
  }

  /**
   * Gets n bl war.
   *
   * @return the n bl war
   */
  public int getNBlWar() {
    return getBaselineWarnings() == null ? 0 : getBaselineWarnings().size();
  }

  /**
   * Gets n it war.
   *
   * @return the n it war
   */
  public int getNItWar(int profile) {
    return getITWarnings(profile) == null ? 0 : getITWarnings(profile).size();
  }

  @Override
  public int compareTo(Object o) {
    if (o instanceof IndividualReport){
      IndividualReport other = (IndividualReport) o;
      Integer thisPercent = calculatePercent();
      Integer otherPercent = other.calculatePercent();
      return thisPercent.compareTo(otherPercent);
    } else {
      return -1;
    }
  }
}
