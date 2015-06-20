package com.tum.identapp;
 
import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import android.app.Application;
 

@ReportsCrashes(
        formKey = "",
        reportType = org.acra.sender.HttpSender.Type.JSON,
        httpMethod = org.acra.sender.HttpSender.Method.PUT,
        formUri = "https://ident.cloudant.com/acra-identification/_design/acra-storage/_update/report",
        formUriBasicAuthLogin = "imentithdrackstsawsevigh",
        formUriBasicAuthPassword = "s1yW4gGsPplx3lsmtE1CDSLR",
        mode = ReportingInteractionMode.SILENT,
        customReportContent = {
        		ReportField.APP_VERSION_CODE,
        		ReportField.APP_VERSION_NAME,
        		ReportField.ANDROID_VERSION,
        		ReportField.PACKAGE_NAME,
        		ReportField.REPORT_ID,
        		ReportField.BUILD,
        		ReportField.STACK_TRACE
        		}

        )

public class AndroidIdentification extends Application
{
  @Override
  public void onCreate()
  {
    super.onCreate();
    //ACRA.init(this);
   
  }
 

   

}