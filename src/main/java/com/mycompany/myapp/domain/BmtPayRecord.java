package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A BmtPayRecord.
 */
@Entity
@Table(name = "bmt_pay_record")
public class BmtPayRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pay_time")
    private Instant payTime;

    @Column(name = "pay_person_id")
    private String payPersonId;

    @Column(name = "pay_person_name")
    private String payPersonName;

    @Column(name = "pay_amount")
    private Long payAmount;

    @ManyToOne
    @JsonIgnoreProperties("bmtPayRecords")
    private BmtChangCi bmtChangCi;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getPayTime() {
        return payTime;
    }

    public BmtPayRecord payTime(Instant payTime) {
        this.payTime = payTime;
        return this;
    }

    public void setPayTime(Instant payTime) {
        this.payTime = payTime;
    }

    public String getPayPersonId() {
        return payPersonId;
    }

    public BmtPayRecord payPersonId(String payPersonId) {
        this.payPersonId = payPersonId;
        return this;
    }

    public void setPayPersonId(String payPersonId) {
        this.payPersonId = payPersonId;
    }

    public String getPayPersonName() {
        return payPersonName;
    }

    public BmtPayRecord payPersonName(String payPersonName) {
        this.payPersonName = payPersonName;
        return this;
    }

    public void setPayPersonName(String payPersonName) {
        this.payPersonName = payPersonName;
    }

    public Long getPayAmount() {
        return payAmount;
    }

    public BmtPayRecord payAmount(Long payAmount) {
        this.payAmount = payAmount;
        return this;
    }

    public void setPayAmount(Long payAmount) {
        this.payAmount = payAmount;
    }

    public BmtChangCi getBmtChangCi() {
        return bmtChangCi;
    }

    public BmtPayRecord bmtChangCi(BmtChangCi bmtChangCi) {
        this.bmtChangCi = bmtChangCi;
        return this;
    }

    public void setBmtChangCi(BmtChangCi bmtChangCi) {
        this.bmtChangCi = bmtChangCi;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BmtPayRecord)) {
            return false;
        }
        return id != null && id.equals(((BmtPayRecord) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BmtPayRecord{" +
            "id=" + getId() +
            ", payTime='" + getPayTime() + "'" +
            ", payPersonId='" + getPayPersonId() + "'" +
            ", payPersonName='" + getPayPersonName() + "'" +
            ", payAmount=" + getPayAmount() +
            "}";
    }
}
