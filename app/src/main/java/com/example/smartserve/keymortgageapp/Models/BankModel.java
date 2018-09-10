package com.example.smartserve.keymortgageapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jwick on 8/20/18.
 */

public class BankModel implements Parcelable {

    String bankId,bankName,bankBranch,bankLogo,bankDomain;

    public BankModel(String bankId, String bankName, String bankBranch, String bankLogo, String bankDomain) {
        this.bankId = bankId;
        this.bankName = bankName;
        this.bankBranch = bankBranch;
        this.bankLogo = bankLogo;
        this.bankDomain = bankDomain;
    }

    protected BankModel(Parcel in) {
        bankId = in.readString();
        bankName = in.readString();
        bankBranch = in.readString();
        bankLogo = in.readString();
        bankDomain = in.readString();
    }

    public static final Creator<BankModel> CREATOR = new Creator<BankModel>() {
        @Override
        public BankModel createFromParcel(Parcel in) {
            return new BankModel(in);
        }

        @Override
        public BankModel[] newArray(int size) {
            return new BankModel[size];
        }
    };

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

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(bankId);
        parcel.writeString(bankName);
        parcel.writeString(bankBranch);
        parcel.writeString(bankLogo);
        parcel.writeString(bankDomain);
    }
}
