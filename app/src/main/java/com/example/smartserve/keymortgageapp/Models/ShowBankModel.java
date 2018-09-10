package com.example.smartserve.keymortgageapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jwick on 8/20/18.
 */

public class ShowBankModel {

    String bankId,bankName,userbankBranch,bankLogo,bankDomain,useremail;

    public ShowBankModel(String bankId, String bankName, String userbankBranch, String bankLogo, String bankDomain, String useremail) {
        this.bankId = bankId;
        this.bankName = bankName;
        this.userbankBranch = userbankBranch;
        this.bankLogo = bankLogo;
        this.bankDomain = bankDomain;
        this.useremail = useremail;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getUserbankBranch() {
        return userbankBranch;
    }

    public void setUserbankBranch(String userbankBranch) {
        this.userbankBranch = userbankBranch;
    }

    public String getBankLogo() {
        return bankLogo;
    }

    public void setBankLogo(String bankLogo) {
        this.bankLogo = bankLogo;
    }

    public String getBankDomain() {
        return bankDomain;
    }

    public void setBankDomain(String bankDomain) {
        this.bankDomain = bankDomain;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }
}
