package com.techvg.covid.care.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.techvg.covid.care.domain.PatientInfo} entity. This class is used
 * in {@link com.techvg.covid.care.web.rest.PatientInfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /patient-infos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PatientInfoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter icmrId;

    private StringFilter srfId;

    private StringFilter labName;

    private StringFilter patientID;

    private StringFilter patientName;

    private StringFilter age;

    private StringFilter ageIn;

    private StringFilter gender;

    private StringFilter nationality;

    private StringFilter address;

    private StringFilter villageTown;

    private StringFilter pincode;

    private StringFilter patientCategory;

    private InstantFilter dateOfSampleCollection;

    private InstantFilter dateOfSampleReceived;

    private StringFilter sampleType;

    private StringFilter sampleId;

    private StringFilter underlyingMedicalCondition;

    private StringFilter hospitalized;

    private StringFilter hospitalName;

    private InstantFilter hospitalizationDate;

    private StringFilter hospitalState;

    private StringFilter hospitalDistrict;

    private StringFilter symptomsStatus;

    private StringFilter symptoms;

    private StringFilter testingKitUsed;

    private StringFilter eGeneNGene;

    private StringFilter ctValueOfEGeneNGene;

    private StringFilter rdRpSGene;

    private StringFilter ctValueOfRdRpSGene;

    private StringFilter oRF1aORF1bNN2Gene;

    private StringFilter ctValueOfORF1aORF1bNN2Gene;

    private StringFilter repeatSample;

    private InstantFilter dateOfSampleTested;

    private InstantFilter entryDate;

    private InstantFilter confirmationDate;

    private StringFilter finalResultSample;

    private StringFilter remarks;

    private InstantFilter editedOn;

    private InstantFilter ccmsPullDate;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter stateId;

    private LongFilter districtId;

    public PatientInfoCriteria() {
    }

    public PatientInfoCriteria(PatientInfoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.icmrId = other.icmrId == null ? null : other.icmrId.copy();
        this.srfId = other.srfId == null ? null : other.srfId.copy();
        this.labName = other.labName == null ? null : other.labName.copy();
        this.patientID = other.patientID == null ? null : other.patientID.copy();
        this.patientName = other.patientName == null ? null : other.patientName.copy();
        this.age = other.age == null ? null : other.age.copy();
        this.ageIn = other.ageIn == null ? null : other.ageIn.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.nationality = other.nationality == null ? null : other.nationality.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.villageTown = other.villageTown == null ? null : other.villageTown.copy();
        this.pincode = other.pincode == null ? null : other.pincode.copy();
        this.patientCategory = other.patientCategory == null ? null : other.patientCategory.copy();
        this.dateOfSampleCollection = other.dateOfSampleCollection == null ? null : other.dateOfSampleCollection.copy();
        this.dateOfSampleReceived = other.dateOfSampleReceived == null ? null : other.dateOfSampleReceived.copy();
        this.sampleType = other.sampleType == null ? null : other.sampleType.copy();
        this.sampleId = other.sampleId == null ? null : other.sampleId.copy();
        this.underlyingMedicalCondition = other.underlyingMedicalCondition == null ? null : other.underlyingMedicalCondition.copy();
        this.hospitalized = other.hospitalized == null ? null : other.hospitalized.copy();
        this.hospitalName = other.hospitalName == null ? null : other.hospitalName.copy();
        this.hospitalizationDate = other.hospitalizationDate == null ? null : other.hospitalizationDate.copy();
        this.hospitalState = other.hospitalState == null ? null : other.hospitalState.copy();
        this.hospitalDistrict = other.hospitalDistrict == null ? null : other.hospitalDistrict.copy();
        this.symptomsStatus = other.symptomsStatus == null ? null : other.symptomsStatus.copy();
        this.symptoms = other.symptoms == null ? null : other.symptoms.copy();
        this.testingKitUsed = other.testingKitUsed == null ? null : other.testingKitUsed.copy();
        this.eGeneNGene = other.eGeneNGene == null ? null : other.eGeneNGene.copy();
        this.ctValueOfEGeneNGene = other.ctValueOfEGeneNGene == null ? null : other.ctValueOfEGeneNGene.copy();
        this.rdRpSGene = other.rdRpSGene == null ? null : other.rdRpSGene.copy();
        this.ctValueOfRdRpSGene = other.ctValueOfRdRpSGene == null ? null : other.ctValueOfRdRpSGene.copy();
        this.oRF1aORF1bNN2Gene = other.oRF1aORF1bNN2Gene == null ? null : other.oRF1aORF1bNN2Gene.copy();
        this.ctValueOfORF1aORF1bNN2Gene = other.ctValueOfORF1aORF1bNN2Gene == null ? null : other.ctValueOfORF1aORF1bNN2Gene.copy();
        this.repeatSample = other.repeatSample == null ? null : other.repeatSample.copy();
        this.dateOfSampleTested = other.dateOfSampleTested == null ? null : other.dateOfSampleTested.copy();
        this.entryDate = other.entryDate == null ? null : other.entryDate.copy();
        this.confirmationDate = other.confirmationDate == null ? null : other.confirmationDate.copy();
        this.finalResultSample = other.finalResultSample == null ? null : other.finalResultSample.copy();
        this.remarks = other.remarks == null ? null : other.remarks.copy();
        this.editedOn = other.editedOn == null ? null : other.editedOn.copy();
        this.ccmsPullDate = other.ccmsPullDate == null ? null : other.ccmsPullDate.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.stateId = other.stateId == null ? null : other.stateId.copy();
        this.districtId = other.districtId == null ? null : other.districtId.copy();
    }

    @Override
    public PatientInfoCriteria copy() {
        return new PatientInfoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getIcmrId() {
        return icmrId;
    }

    public void setIcmrId(StringFilter icmrId) {
        this.icmrId = icmrId;
    }

    public StringFilter getSrfId() {
        return srfId;
    }

    public void setSrfId(StringFilter srfId) {
        this.srfId = srfId;
    }

    public StringFilter getLabName() {
        return labName;
    }

    public void setLabName(StringFilter labName) {
        this.labName = labName;
    }

    public StringFilter getPatientID() {
        return patientID;
    }

    public void setPatientID(StringFilter patientID) {
        this.patientID = patientID;
    }

    public StringFilter getPatientName() {
        return patientName;
    }

    public void setPatientName(StringFilter patientName) {
        this.patientName = patientName;
    }

    public StringFilter getAge() {
        return age;
    }

    public void setAge(StringFilter age) {
        this.age = age;
    }

    public StringFilter getAgeIn() {
        return ageIn;
    }

    public void setAgeIn(StringFilter ageIn) {
        this.ageIn = ageIn;
    }

    public StringFilter getGender() {
        return gender;
    }

    public void setGender(StringFilter gender) {
        this.gender = gender;
    }

    public StringFilter getNationality() {
        return nationality;
    }

    public void setNationality(StringFilter nationality) {
        this.nationality = nationality;
    }

    public StringFilter getAddress() {
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getVillageTown() {
        return villageTown;
    }

    public void setVillageTown(StringFilter villageTown) {
        this.villageTown = villageTown;
    }

    public StringFilter getPincode() {
        return pincode;
    }

    public void setPincode(StringFilter pincode) {
        this.pincode = pincode;
    }

    public StringFilter getPatientCategory() {
        return patientCategory;
    }

    public void setPatientCategory(StringFilter patientCategory) {
        this.patientCategory = patientCategory;
    }

    public InstantFilter getDateOfSampleCollection() {
        return dateOfSampleCollection;
    }

    public void setDateOfSampleCollection(InstantFilter dateOfSampleCollection) {
        this.dateOfSampleCollection = dateOfSampleCollection;
    }

    public InstantFilter getDateOfSampleReceived() {
        return dateOfSampleReceived;
    }

    public void setDateOfSampleReceived(InstantFilter dateOfSampleReceived) {
        this.dateOfSampleReceived = dateOfSampleReceived;
    }

    public StringFilter getSampleType() {
        return sampleType;
    }

    public void setSampleType(StringFilter sampleType) {
        this.sampleType = sampleType;
    }

    public StringFilter getSampleId() {
        return sampleId;
    }

    public void setSampleId(StringFilter sampleId) {
        this.sampleId = sampleId;
    }

    public StringFilter getUnderlyingMedicalCondition() {
        return underlyingMedicalCondition;
    }

    public void setUnderlyingMedicalCondition(StringFilter underlyingMedicalCondition) {
        this.underlyingMedicalCondition = underlyingMedicalCondition;
    }

    public StringFilter getHospitalized() {
        return hospitalized;
    }

    public void setHospitalized(StringFilter hospitalized) {
        this.hospitalized = hospitalized;
    }

    public StringFilter getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(StringFilter hospitalName) {
        this.hospitalName = hospitalName;
    }

    public InstantFilter getHospitalizationDate() {
        return hospitalizationDate;
    }

    public void setHospitalizationDate(InstantFilter hospitalizationDate) {
        this.hospitalizationDate = hospitalizationDate;
    }

    public StringFilter getHospitalState() {
        return hospitalState;
    }

    public void setHospitalState(StringFilter hospitalState) {
        this.hospitalState = hospitalState;
    }

    public StringFilter getHospitalDistrict() {
        return hospitalDistrict;
    }

    public void setHospitalDistrict(StringFilter hospitalDistrict) {
        this.hospitalDistrict = hospitalDistrict;
    }

    public StringFilter getSymptomsStatus() {
        return symptomsStatus;
    }

    public void setSymptomsStatus(StringFilter symptomsStatus) {
        this.symptomsStatus = symptomsStatus;
    }

    public StringFilter getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(StringFilter symptoms) {
        this.symptoms = symptoms;
    }

    public StringFilter getTestingKitUsed() {
        return testingKitUsed;
    }

    public void setTestingKitUsed(StringFilter testingKitUsed) {
        this.testingKitUsed = testingKitUsed;
    }

    public StringFilter geteGeneNGene() {
        return eGeneNGene;
    }

    public void seteGeneNGene(StringFilter eGeneNGene) {
        this.eGeneNGene = eGeneNGene;
    }

    public StringFilter getCtValueOfEGeneNGene() {
        return ctValueOfEGeneNGene;
    }

    public void setCtValueOfEGeneNGene(StringFilter ctValueOfEGeneNGene) {
        this.ctValueOfEGeneNGene = ctValueOfEGeneNGene;
    }

    public StringFilter getRdRpSGene() {
        return rdRpSGene;
    }

    public void setRdRpSGene(StringFilter rdRpSGene) {
        this.rdRpSGene = rdRpSGene;
    }

    public StringFilter getCtValueOfRdRpSGene() {
        return ctValueOfRdRpSGene;
    }

    public void setCtValueOfRdRpSGene(StringFilter ctValueOfRdRpSGene) {
        this.ctValueOfRdRpSGene = ctValueOfRdRpSGene;
    }

    public StringFilter getoRF1aORF1bNN2Gene() {
        return oRF1aORF1bNN2Gene;
    }

    public void setoRF1aORF1bNN2Gene(StringFilter oRF1aORF1bNN2Gene) {
        this.oRF1aORF1bNN2Gene = oRF1aORF1bNN2Gene;
    }

    public StringFilter getCtValueOfORF1aORF1bNN2Gene() {
        return ctValueOfORF1aORF1bNN2Gene;
    }

    public void setCtValueOfORF1aORF1bNN2Gene(StringFilter ctValueOfORF1aORF1bNN2Gene) {
        this.ctValueOfORF1aORF1bNN2Gene = ctValueOfORF1aORF1bNN2Gene;
    }

    public StringFilter getRepeatSample() {
        return repeatSample;
    }

    public void setRepeatSample(StringFilter repeatSample) {
        this.repeatSample = repeatSample;
    }

    public InstantFilter getDateOfSampleTested() {
        return dateOfSampleTested;
    }

    public void setDateOfSampleTested(InstantFilter dateOfSampleTested) {
        this.dateOfSampleTested = dateOfSampleTested;
    }

    public InstantFilter getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(InstantFilter entryDate) {
        this.entryDate = entryDate;
    }

    public InstantFilter getConfirmationDate() {
        return confirmationDate;
    }

    public void setConfirmationDate(InstantFilter confirmationDate) {
        this.confirmationDate = confirmationDate;
    }

    public StringFilter getFinalResultSample() {
        return finalResultSample;
    }

    public void setFinalResultSample(StringFilter finalResultSample) {
        this.finalResultSample = finalResultSample;
    }

    public StringFilter getRemarks() {
        return remarks;
    }

    public void setRemarks(StringFilter remarks) {
        this.remarks = remarks;
    }

    public InstantFilter getEditedOn() {
        return editedOn;
    }

    public void setEditedOn(InstantFilter editedOn) {
        this.editedOn = editedOn;
    }

    public InstantFilter getCcmsPullDate() {
        return ccmsPullDate;
    }

    public void setCcmsPullDate(InstantFilter ccmsPullDate) {
        this.ccmsPullDate = ccmsPullDate;
    }

    public InstantFilter getLastModified() {
        return lastModified;
    }

    public void setLastModified(InstantFilter lastModified) {
        this.lastModified = lastModified;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LongFilter getStateId() {
        return stateId;
    }

    public void setStateId(LongFilter stateId) {
        this.stateId = stateId;
    }

    public LongFilter getDistrictId() {
        return districtId;
    }

    public void setDistrictId(LongFilter districtId) {
        this.districtId = districtId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PatientInfoCriteria that = (PatientInfoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(icmrId, that.icmrId) &&
            Objects.equals(srfId, that.srfId) &&
            Objects.equals(labName, that.labName) &&
            Objects.equals(patientID, that.patientID) &&
            Objects.equals(patientName, that.patientName) &&
            Objects.equals(age, that.age) &&
            Objects.equals(ageIn, that.ageIn) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(nationality, that.nationality) &&
            Objects.equals(address, that.address) &&
            Objects.equals(villageTown, that.villageTown) &&
            Objects.equals(pincode, that.pincode) &&
            Objects.equals(patientCategory, that.patientCategory) &&
            Objects.equals(dateOfSampleCollection, that.dateOfSampleCollection) &&
            Objects.equals(dateOfSampleReceived, that.dateOfSampleReceived) &&
            Objects.equals(sampleType, that.sampleType) &&
            Objects.equals(sampleId, that.sampleId) &&
            Objects.equals(underlyingMedicalCondition, that.underlyingMedicalCondition) &&
            Objects.equals(hospitalized, that.hospitalized) &&
            Objects.equals(hospitalName, that.hospitalName) &&
            Objects.equals(hospitalizationDate, that.hospitalizationDate) &&
            Objects.equals(hospitalState, that.hospitalState) &&
            Objects.equals(hospitalDistrict, that.hospitalDistrict) &&
            Objects.equals(symptomsStatus, that.symptomsStatus) &&
            Objects.equals(symptoms, that.symptoms) &&
            Objects.equals(testingKitUsed, that.testingKitUsed) &&
            Objects.equals(eGeneNGene, that.eGeneNGene) &&
            Objects.equals(ctValueOfEGeneNGene, that.ctValueOfEGeneNGene) &&
            Objects.equals(rdRpSGene, that.rdRpSGene) &&
            Objects.equals(ctValueOfRdRpSGene, that.ctValueOfRdRpSGene) &&
            Objects.equals(oRF1aORF1bNN2Gene, that.oRF1aORF1bNN2Gene) &&
            Objects.equals(ctValueOfORF1aORF1bNN2Gene, that.ctValueOfORF1aORF1bNN2Gene) &&
            Objects.equals(repeatSample, that.repeatSample) &&
            Objects.equals(dateOfSampleTested, that.dateOfSampleTested) &&
            Objects.equals(entryDate, that.entryDate) &&
            Objects.equals(confirmationDate, that.confirmationDate) &&
            Objects.equals(finalResultSample, that.finalResultSample) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(editedOn, that.editedOn) &&
            Objects.equals(ccmsPullDate, that.ccmsPullDate) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(stateId, that.stateId) &&
            Objects.equals(districtId, that.districtId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        icmrId,
        srfId,
        labName,
        patientID,
        patientName,
        age,
        ageIn,
        gender,
        nationality,
        address,
        villageTown,
        pincode,
        patientCategory,
        dateOfSampleCollection,
        dateOfSampleReceived,
        sampleType,
        sampleId,
        underlyingMedicalCondition,
        hospitalized,
        hospitalName,
        hospitalizationDate,
        hospitalState,
        hospitalDistrict,
        symptomsStatus,
        symptoms,
        testingKitUsed,
        eGeneNGene,
        ctValueOfEGeneNGene,
        rdRpSGene,
        ctValueOfRdRpSGene,
        oRF1aORF1bNN2Gene,
        ctValueOfORF1aORF1bNN2Gene,
        repeatSample,
        dateOfSampleTested,
        entryDate,
        confirmationDate,
        finalResultSample,
        remarks,
        editedOn,
        ccmsPullDate,
        lastModified,
        lastModifiedBy,
        stateId,
        districtId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PatientInfoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (icmrId != null ? "icmrId=" + icmrId + ", " : "") +
                (srfId != null ? "srfId=" + srfId + ", " : "") +
                (labName != null ? "labName=" + labName + ", " : "") +
                (patientID != null ? "patientID=" + patientID + ", " : "") +
                (patientName != null ? "patientName=" + patientName + ", " : "") +
                (age != null ? "age=" + age + ", " : "") +
                (ageIn != null ? "ageIn=" + ageIn + ", " : "") +
                (gender != null ? "gender=" + gender + ", " : "") +
                (nationality != null ? "nationality=" + nationality + ", " : "") +
                (address != null ? "address=" + address + ", " : "") +
                (villageTown != null ? "villageTown=" + villageTown + ", " : "") +
                (pincode != null ? "pincode=" + pincode + ", " : "") +
                (patientCategory != null ? "patientCategory=" + patientCategory + ", " : "") +
                (dateOfSampleCollection != null ? "dateOfSampleCollection=" + dateOfSampleCollection + ", " : "") +
                (dateOfSampleReceived != null ? "dateOfSampleReceived=" + dateOfSampleReceived + ", " : "") +
                (sampleType != null ? "sampleType=" + sampleType + ", " : "") +
                (sampleId != null ? "sampleId=" + sampleId + ", " : "") +
                (underlyingMedicalCondition != null ? "underlyingMedicalCondition=" + underlyingMedicalCondition + ", " : "") +
                (hospitalized != null ? "hospitalized=" + hospitalized + ", " : "") +
                (hospitalName != null ? "hospitalName=" + hospitalName + ", " : "") +
                (hospitalizationDate != null ? "hospitalizationDate=" + hospitalizationDate + ", " : "") +
                (hospitalState != null ? "hospitalState=" + hospitalState + ", " : "") +
                (hospitalDistrict != null ? "hospitalDistrict=" + hospitalDistrict + ", " : "") +
                (symptomsStatus != null ? "symptomsStatus=" + symptomsStatus + ", " : "") +
                (symptoms != null ? "symptoms=" + symptoms + ", " : "") +
                (testingKitUsed != null ? "testingKitUsed=" + testingKitUsed + ", " : "") +
                (eGeneNGene != null ? "eGeneNGene=" + eGeneNGene + ", " : "") +
                (ctValueOfEGeneNGene != null ? "ctValueOfEGeneNGene=" + ctValueOfEGeneNGene + ", " : "") +
                (rdRpSGene != null ? "rdRpSGene=" + rdRpSGene + ", " : "") +
                (ctValueOfRdRpSGene != null ? "ctValueOfRdRpSGene=" + ctValueOfRdRpSGene + ", " : "") +
                (oRF1aORF1bNN2Gene != null ? "oRF1aORF1bNN2Gene=" + oRF1aORF1bNN2Gene + ", " : "") +
                (ctValueOfORF1aORF1bNN2Gene != null ? "ctValueOfORF1aORF1bNN2Gene=" + ctValueOfORF1aORF1bNN2Gene + ", " : "") +
                (repeatSample != null ? "repeatSample=" + repeatSample + ", " : "") +
                (dateOfSampleTested != null ? "dateOfSampleTested=" + dateOfSampleTested + ", " : "") +
                (entryDate != null ? "entryDate=" + entryDate + ", " : "") +
                (confirmationDate != null ? "confirmationDate=" + confirmationDate + ", " : "") +
                (finalResultSample != null ? "finalResultSample=" + finalResultSample + ", " : "") +
                (remarks != null ? "remarks=" + remarks + ", " : "") +
                (editedOn != null ? "editedOn=" + editedOn + ", " : "") +
                (ccmsPullDate != null ? "ccmsPullDate=" + ccmsPullDate + ", " : "") +
                (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (stateId != null ? "stateId=" + stateId + ", " : "") +
                (districtId != null ? "districtId=" + districtId + ", " : "") +
            "}";
    }

}
