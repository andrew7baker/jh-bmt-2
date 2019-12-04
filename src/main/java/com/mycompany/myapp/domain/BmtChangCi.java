package com.mycompany.myapp.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A BmtChangCi.
 */
@Entity
@Table(name = "bmt_chang_ci")
public class BmtChangCi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "week_day")
    private String weekDay;

    @Column(name = "time_begin")
    private Instant timeBegin;

    @Column(name = "time_end")
    private Instant timeEnd;

    @Column(name = "owner")
    private String owner;

    @Column(name = "user_id")
    private Long userId;

    @OneToMany(mappedBy = "bmtChangCi")
    private Set<BmtPayRecord> bmtPayRecords = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public BmtChangCi name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public BmtChangCi weekDay(String weekDay) {
        this.weekDay = weekDay;
        return this;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public Instant getTimeBegin() {
        return timeBegin;
    }

    public BmtChangCi timeBegin(Instant timeBegin) {
        this.timeBegin = timeBegin;
        return this;
    }

    public void setTimeBegin(Instant timeBegin) {
        this.timeBegin = timeBegin;
    }

    public Instant getTimeEnd() {
        return timeEnd;
    }

    public BmtChangCi timeEnd(Instant timeEnd) {
        this.timeEnd = timeEnd;
        return this;
    }

    public void setTimeEnd(Instant timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getOwner() {
        return owner;
    }

    public BmtChangCi owner(String owner) {
        this.owner = owner;
        return this;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Long getUserId() {
        return userId;
    }

    public BmtChangCi userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<BmtPayRecord> getBmtPayRecords() {
        return bmtPayRecords;
    }

    public BmtChangCi bmtPayRecords(Set<BmtPayRecord> bmtPayRecords) {
        this.bmtPayRecords = bmtPayRecords;
        return this;
    }

    public BmtChangCi addBmtPayRecord(BmtPayRecord bmtPayRecord) {
        this.bmtPayRecords.add(bmtPayRecord);
        bmtPayRecord.setBmtChangCi(this);
        return this;
    }

    public BmtChangCi removeBmtPayRecord(BmtPayRecord bmtPayRecord) {
        this.bmtPayRecords.remove(bmtPayRecord);
        bmtPayRecord.setBmtChangCi(null);
        return this;
    }

    public void setBmtPayRecords(Set<BmtPayRecord> bmtPayRecords) {
        this.bmtPayRecords = bmtPayRecords;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BmtChangCi)) {
            return false;
        }
        return id != null && id.equals(((BmtChangCi) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BmtChangCi{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", weekDay='" + getWeekDay() + "'" +
            ", timeBegin='" + getTimeBegin() + "'" +
            ", timeEnd='" + getTimeEnd() + "'" +
            ", owner='" + getOwner() + "'" +
            ", userId=" + getUserId() +
            "}";
    }
}
