/**
 * This Log Class provide the data for to registry and consequently, save logs in the file.
 * @author Lu Sacramento
 * @version 2.0.0 Build Aug 23, 2022.
 */

package com.api.models.entity;

public class Log {

  private String option, original, generated;
  private boolean isValid;

  public Log(
    String option,
    String original,
    String generated,
    boolean isValid
  ) {
    this.option = option;
    this.original = original;
    this.generated = generated;
    this.isValid = isValid;
  }

  public String getOption() {
    return this.option;
  }

  public void setOption(String option) {
    this.option = option;
  }

  public String getOriginal() {
    return this.original;
  }

  public void setOriginal(String original) {
    this.original = original;
  }

  public String getGenerated() {
    return this.generated;
  }

  public void setGenerated(String generated) {
    this.generated = generated;
  }

  public boolean getIsValid() {
    return this.isValid;
  }

  public void setIsValid(boolean isValid) {
    this.isValid = isValid;
  }
}
