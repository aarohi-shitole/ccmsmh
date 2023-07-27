package com.techvg.covid.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A PatientInfo.
 */
@Entity
@Table(name = "patient_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PatientInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "icmr_id", nullable = false, unique = true)
    private String icmrId;

    @Column(name = "srf_id")
    private String srfId;

    @Column(name = "lab_name")
    private String labName;

    @Column(name = "patient_id")
    private String patientID;

    @Column(name = "patient_name")
    private String patientName;
    
    @Column(name = "aadhar_card_number")
    private String aadharCardNumber;
    
    @Column(name = "passport_number")
    private String passportNumber;
    

    public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	@Column(name = "contact_number")
    public String contactNumber;
    
    @Column(name = "contact_number_belongs_to")
    public String contactNumberBelongsTo;
    
    @Column(name = "age")
    private String age;

    @Column(name = "age_in")
    private String ageIn;

    @Column(name = "gender")
    private String gender;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "address")
    private String address;

    @Column(name = "village_town")
    private String villageTown;

    @Column(name = "pincode")
    private String pincode;

    @Column(name = "patient_category")
    private String patientCategory;

    @Column(name = "date_of_sample_collection")
    private Instant dateOfSampleCollection;

    @Column(name = "date_of_sample_received")
    private Instant dateOfSampleReceived;

    @Column(name = "sample_type")
    private String sampleType;

    @Column(name = "sample_id")
    private String sampleId;

    @Column(name = "underlying_medical_condition")
    private String underlyingMedicalCondition;

    @Column(name = "hospitalized")
    private String hospitalized;

    @Column(name = "hospital_name")
    private String hospitalName;

    @Column(name = "hospitalization_date")
    private Instant hospitalizationDate;

    @Column(name = "hospital_state")
    private String hospitalState;

    @Column(name = "hospital_district")
    private String hospitalDistrict;

    @Column(name = "symptoms_status")
    private String symptomsStatus;

    @Column(name = "symptoms")
    private String symptoms;

    @Column(name = "testing_kit_used")
    private String testingKitUsed;

    @Column(name = "e_gene_n_gene")
    private String eGeneNGene;

    @Column(name = "ct_value_of_e_gene_n_gene")
    private String ctValueOfEGeneNGene;

    @Column(name = "rd_rp_s_gene")
    private String rdRpSGene;

    @Column(name = "ct_value_of_rd_rp_s_gene")
    private String ctValueOfRdRpSGene;

    @Column(name = "o_rf_1_a_orf_1_b_nn_2_gene")
    private String oRF1aORF1bNN2Gene;

    @Column(name = "ct_value_of_orf_1_a_orf_1_b_nn_2_gene")
    private String ctValueOfORF1aORF1bNN2Gene;

    @Column(name = "repeat_sample")
    private String repeatSample;

    @Column(name = "date_of_sample_tested")
    private Instant dateOfSampleTested;

    @Column(name = "entry_date")
    private Instant entryDate;

    @Column(name = "confirmation_date")
    private Instant confirmationDate;

    @Column(name = "final_result_sample")
    private String finalResultSample;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "edited_on")
    private Instant editedOn;

    @NotNull
    @Column(name = "ccms_pull_date", nullable = false)
    private Instant ccmsPullDate;

    @NotNull
    @Column(name = "last_modified", nullable = false)
    private Instant lastModified;

    @NotNull
    @Column(name = "last_modified_by", nullable = false)
    private String lastModifiedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = "patientInfos", allowSetters = true)
    private State state;

    @ManyToOne
    @JsonIgnoreProperties(value = "patientInfos", allowSetters = true)
    private District district;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIcmrId() {
        return icmrId;
    }

    public PatientInfo icmrId(String icmrId) {
        this.icmrId = icmrId;
        return this;
    }

    public void setIcmrId(String icmrId) {
        this.icmrId = icmrId;
    }

    public String getSrfId() {
        return srfId;
    }

    public PatientInfo srfId(String srfId) {
        this.srfId = srfId;
        return this;
    }

    public void setSrfId(String srfId) {
        this.srfId = srfId;
    }

    public String getLabName() {
        return labName;
    }

    public PatientInfo labName(String labName) {
        this.labName = labName;
        return this;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getPatientID() {
        return patientID;
    }

    public PatientInfo patientID(String patientID) {
        this.patientID = patientID;
        return this;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getPatientName() {
        return patientName;
    }

    public PatientInfo patientName(String patientName) {
        this.patientName = patientName;
        return this;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    
    
    public String getAadharCardNumber() {
		return aadharCardNumber;
	}

	public void setAadharCardNumber(String aadharCardNumber) {
		this.aadharCardNumber = aadharCardNumber;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	
	

	public String getContactNumberBelongsTo() {
		return contactNumberBelongsTo;
	}

	public void setContactNumberBelongsTo(String contactNumberBelongsTo) {
		this.contactNumberBelongsTo = contactNumberBelongsTo;
	}

	public String getAge() {
        return age;
    }

    public PatientInfo age(String age) {
        this.age = age;
        return this;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAgeIn() {
        return ageIn;
    }

    public PatientInfo ageIn(String ageIn) {
        this.ageIn = ageIn;
        return this;
    }

    public void setAgeIn(String ageIn) {
        this.ageIn = ageIn;
    }

    public String getGender() {
        return gender;
    }

    public PatientInfo gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public PatientInfo nationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getAddress() {
        return address;
    }

    public PatientInfo address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVillageTown() {
        return villageTown;
    }

    public PatientInfo villageTown(String villageTown) {
        this.villageTown = villageTown;
        return this;
    }

    public void setVillageTown(String villageTown) {
        this.villageTown = villageTown;
    }

    public String getPincode() {
        return pincode;
    }

    public PatientInfo pincode(String pincode) {
        this.pincode = pincode;
        return this;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPatientCategory() {
        return patientCategory;
    }

    public PatientInfo patientCategory(String patientCategory) {
        this.patientCategory = patientCategory;
        return this;
    }

    public void setPatientCategory(String patientCategory) {
        this.patientCategory = patientCategory;
    }

    public Instant getDateOfSampleCollection() {
        return dateOfSampleCollection;
    }

    public PatientInfo dateOfSampleCollection(Instant dateOfSampleCollection) {
        this.dateOfSampleCollection = dateOfSampleCollection;
        return this;
    }

    public void setDateOfSampleCollection(Instant dateOfSampleCollection) {
        this.dateOfSampleCollection = dateOfSampleCollection;
    }

    public Instant getDateOfSampleReceived() {
        return dateOfSampleReceived;
    }

    public PatientInfo dateOfSampleReceived(Instant dateOfSampleReceived) {
        this.dateOfSampleReceived = dateOfSampleReceived;
        return this;
    }

    public void setDateOfSampleReceived(Instant dateOfSampleReceived) {
        this.dateOfSampleReceived = dateOfSampleReceived;
    }

    public String getSampleType() {
        return sampleType;
    }

    public PatientInfo sampleType(String sampleType) {
        this.sampleType = sampleType;
        return this;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getSampleId() {
        return sampleId;
    }

    public PatientInfo sampleId(String sampleId) {
        this.sampleId = sampleId;
        return this;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getUnderlyingMedicalCondition() {
        return underlyingMedicalCondition;
    }

    public PatientInfo underlyingMedicalCondition(String underlyingMedicalCondition) {
        this.underlyingMedicalCondition = underlyingMedicalCondition;
        return this;
    }

    public void setUnderlyingMedicalCondition(String underlyingMedicalCondition) {
        this.underlyingMedicalCondition = underlyingMedicalCondition;
    }

    public String getHospitalized() {
        return hospitalized;
    }

    public PatientInfo hospitalized(String hospitalized) {
        this.hospitalized = hospitalized;
        return this;
    }

    public void setHospitalized(String hospitalized) {
        this.hospitalized = hospitalized;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public PatientInfo hospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
        return this;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public Instant getHospitalizationDate() {
        return hospitalizationDate;
    }

    public PatientInfo hospitalizationDate(Instant hospitalizationDate) {
        this.hospitalizationDate = hospitalizationDate;
        return this;
    }

    public void setHospitalizationDate(Instant hospitalizationDate) {
        this.hospitalizationDate = hospitalizationDate;
    }

    public String getHospitalState() {
        return hospitalState;
    }

    public PatientInfo hospitalState(String hospitalState) {
        this.hospitalState = hospitalState;
        return this;
    }

    public void setHospitalState(String hospitalState) {
        this.hospitalState = hospitalState;
    }

    public String getHospitalDistrict() {
        return hospitalDistrict;
    }

    public PatientInfo hospitalDistrict(String hospitalDistrict) {
        this.hospitalDistrict = hospitalDistrict;
        return this;
    }

    public void setHospitalDistrict(String hospitalDistrict) {
        this.hospitalDistrict = hospitalDistrict;
    }

    public String getSymptomsStatus() {
        return symptomsStatus;
    }

    public PatientInfo symptomsStatus(String symptomsStatus) {
        this.symptomsStatus = symptomsStatus;
        return this;
    }

    public void setSymptomsStatus(String symptomsStatus) {
        this.symptomsStatus = symptomsStatus;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public PatientInfo symptoms(String symptoms) {
        this.symptoms = symptoms;
        return this;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getTestingKitUsed() {
        return testingKitUsed;
    }

    public PatientInfo testingKitUsed(String testingKitUsed) {
        this.testingKitUsed = testingKitUsed;
        return this;
    }

    public void setTestingKitUsed(String testingKitUsed) {
        this.testingKitUsed = testingKitUsed;
    }

    public String geteGeneNGene() {
        return eGeneNGene;
    }

    public PatientInfo eGeneNGene(String eGeneNGene) {
        this.eGeneNGene = eGeneNGene;
        return this;
    }

    public void seteGeneNGene(String eGeneNGene) {
        this.eGeneNGene = eGeneNGene;
    }

    public String getCtValueOfEGeneNGene() {
        return ctValueOfEGeneNGene;
    }

    public PatientInfo ctValueOfEGeneNGene(String ctValueOfEGeneNGene) {
        this.ctValueOfEGeneNGene = ctValueOfEGeneNGene;
        return this;
    }

    public void setCtValueOfEGeneNGene(String ctValueOfEGeneNGene) {
        this.ctValueOfEGeneNGene = ctValueOfEGeneNGene;
    }

    public String getRdRpSGene() {
        return rdRpSGene;
    }

    public PatientInfo rdRpSGene(String rdRpSGene) {
        this.rdRpSGene = rdRpSGene;
        return this;
    }

    public void setRdRpSGene(String rdRpSGene) {
        this.rdRpSGene = rdRpSGene;
    }

    public String getCtValueOfRdRpSGene() {
        return ctValueOfRdRpSGene;
    }

    public PatientInfo ctValueOfRdRpSGene(String ctValueOfRdRpSGene) {
        this.ctValueOfRdRpSGene = ctValueOfRdRpSGene;
        return this;
    }

    public void setCtValueOfRdRpSGene(String ctValueOfRdRpSGene) {
        this.ctValueOfRdRpSGene = ctValueOfRdRpSGene;
    }

    public String getoRF1aORF1bNN2Gene() {
        return oRF1aORF1bNN2Gene;
    }

    public PatientInfo oRF1aORF1bNN2Gene(String oRF1aORF1bNN2Gene) {
        this.oRF1aORF1bNN2Gene = oRF1aORF1bNN2Gene;
        return this;
    }

    public void setoRF1aORF1bNN2Gene(String oRF1aORF1bNN2Gene) {
        this.oRF1aORF1bNN2Gene = oRF1aORF1bNN2Gene;
    }

    public String getCtValueOfORF1aORF1bNN2Gene() {
        return ctValueOfORF1aORF1bNN2Gene;
    }

    public PatientInfo ctValueOfORF1aORF1bNN2Gene(String ctValueOfORF1aORF1bNN2Gene) {
        this.ctValueOfORF1aORF1bNN2Gene = ctValueOfORF1aORF1bNN2Gene;
        return this;
    }

    public void setCtValueOfORF1aORF1bNN2Gene(String ctValueOfORF1aORF1bNN2Gene) {
        this.ctValueOfORF1aORF1bNN2Gene = ctValueOfORF1aORF1bNN2Gene;
    }

    public String getRepeatSample() {
        return repeatSample;
    }

    public PatientInfo repeatSample(String repeatSample) {
        this.repeatSample = repeatSample;
        return this;
    }

    public void setRepeatSample(String repeatSample) {
        this.repeatSample = repeatSample;
    }

    public Instant getDateOfSampleTested() {
        return dateOfSampleTested;
    }

    public PatientInfo dateOfSampleTested(Instant dateOfSampleTested) {
        this.dateOfSampleTested = dateOfSampleTested;
        return this;
    }

    public void setDateOfSampleTested(Instant dateOfSampleTested) {
        this.dateOfSampleTested = dateOfSampleTested;
    }

    public Instant getEntryDate() {
        return entryDate;
    }

    public PatientInfo entryDate(Instant entryDate) {
        this.entryDate = entryDate;
        return this;
    }

    public void setEntryDate(Instant entryDate) {
        this.entryDate = entryDate;
    }

    public Instant getConfirmationDate() {
        return confirmationDate;
    }

    public PatientInfo confirmationDate(Instant confirmationDate) {
        this.confirmationDate = confirmationDate;
        return this;
    }

    public void setConfirmationDate(Instant confirmationDate) {
        this.confirmationDate = confirmationDate;
    }

    public String getFinalResultSample() {
        return finalResultSample;
    }

    public PatientInfo finalResultSample(String finalResultSample) {
        this.finalResultSample = finalResultSample;
        return this;
    }

    public void setFinalResultSample(String finalResultSample) {
        this.finalResultSample = finalResultSample;
    }

    public String getRemarks() {
        return remarks;
    }

    public PatientInfo remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Instant getEditedOn() {
        return editedOn;
    }

    public PatientInfo editedOn(Instant editedOn) {
        this.editedOn = editedOn;
        return this;
    }

    public void setEditedOn(Instant editedOn) {
        this.editedOn = editedOn;
    }

    public Instant getCcmsPullDate() {
        return ccmsPullDate;
    }

    public PatientInfo ccmsPullDate(Instant ccmsPullDate) {
        this.ccmsPullDate = ccmsPullDate;
        return this;
    }

    public void setCcmsPullDate(Instant ccmsPullDate) {
        this.ccmsPullDate = ccmsPullDate;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public PatientInfo lastModified(Instant lastModified) {
        this.lastModified = lastModified;
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public PatientInfo lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public State getState() {
        return state;
    }

    public PatientInfo state(State state) {
        this.state = state;
        return this;
    }

    public void setState(State state) {
        this.state = state;
    }

    public District getDistrict() {
        return district;
    }

    public PatientInfo district(District district) {
        this.district = district;
        return this;
    }

    public void setDistrict(District district) {
        this.district = district;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PatientInfo)) {
            return false;
        }
        return id != null && id.equals(((PatientInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PatientInfo{" +
            "id=" + getId() +
            ", icmrId='" + getIcmrId() + "'" +
            ", srfId='" + getSrfId() + "'" +
            ", labName='" + getLabName() + "'" +
            ", patientID='" + getPatientID() + "'" +
            ", patientName='" + getPatientName() + "'" +
            ", contactNumber='" + getContactNumber() + "'" +
            ", age='" + getAge() + "'" +
            ", ageIn='" + getAgeIn() + "'" +
            ", gender='" + getGender() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", address='" + getAddress() + "'" +
            ", villageTown='" + getVillageTown() + "'" +
            ", pincode='" + getPincode() + "'" +
            ", patientCategory='" + getPatientCategory() + "'" +
            ", dateOfSampleCollection='" + getDateOfSampleCollection() + "'" +
            ", dateOfSampleReceived='" + getDateOfSampleReceived() + "'" +
            ", sampleType='" + getSampleType() + "'" +
            ", sampleId='" + getSampleId() + "'" +
            ", underlyingMedicalCondition='" + getUnderlyingMedicalCondition() + "'" +
            ", hospitalized='" + getHospitalized() + "'" +
            ", hospitalName='" + getHospitalName() + "'" +
            ", hospitalizationDate='" + getHospitalizationDate() + "'" +
            ", hospitalState='" + getHospitalState() + "'" +
            ", hospitalDistrict='" + getHospitalDistrict() + "'" +
            ", symptomsStatus='" + getSymptomsStatus() + "'" +
            ", symptoms='" + getSymptoms() + "'" +
            ", testingKitUsed='" + getTestingKitUsed() + "'" +
            ", eGeneNGene='" + geteGeneNGene() + "'" +
            ", ctValueOfEGeneNGene='" + getCtValueOfEGeneNGene() + "'" +
            ", rdRpSGene='" + getRdRpSGene() + "'" +
            ", ctValueOfRdRpSGene='" + getCtValueOfRdRpSGene() + "'" +
            ", oRF1aORF1bNN2Gene='" + getoRF1aORF1bNN2Gene() + "'" +
            ", ctValueOfORF1aORF1bNN2Gene='" + getCtValueOfORF1aORF1bNN2Gene() + "'" +
            ", repeatSample='" + getRepeatSample() + "'" +
            ", dateOfSampleTested='" + getDateOfSampleTested() + "'" +
            ", entryDate='" + getEntryDate() + "'" +
            ", confirmationDate='" + getConfirmationDate() + "'" +
            ", finalResultSample='" + getFinalResultSample() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", editedOn='" + getEditedOn() + "'" +
            ", ccmsPullDate='" + getCcmsPullDate() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
