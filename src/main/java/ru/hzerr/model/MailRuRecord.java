package ru.hzerr.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.hzerr.util.JsonToStringStyle;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MailRuRecord implements Serializable {

    private boolean created;
    private CreationStatus creationStatus;
    private LocalDateTime createdDate;
    private String login;
    private String password;
    private String reservedEmail;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private Gender gender;
    private boolean blocked;

    public boolean isCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }

    public CreationStatus getCreationStatus() {
        return creationStatus;
    }

    public void setCreationStatus(CreationStatus creationStatus) {
        this.creationStatus = creationStatus;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReservedEmail() {
        return reservedEmail;
    }

    public void setReservedEmail(String reservedEmail) {
        this.reservedEmail = reservedEmail;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o instanceof MailRuRecord account) {
            return new EqualsBuilder()
                    .append(created, account.created)
                    .append(blocked, account.blocked)
                    .append(creationStatus, account.creationStatus)
                    .append(createdDate, account.createdDate)
                    .append(login, account.login)
                    .append(password, account.password)
                    .append(reservedEmail, account.reservedEmail)
                    .append(firstName, account.firstName)
                    .append(lastName, account.lastName)
                    .append(dateOfBirth, account.dateOfBirth)
                    .append(gender, account.gender)
                    .isEquals();
        }

        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(created)
                .append(creationStatus)
                .append(createdDate)
                .append(login)
                .append(password)
                .append(reservedEmail)
                .append(firstName)
                .append(lastName)
                .append(dateOfBirth)
                .append(gender)
                .append(blocked)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, new JsonToStringStyle())
                .append("created", created)
                .append("creationStatus", creationStatus)
                .append("createdDate", createdDate)
                .append("login", login)
                .append("password", password)
                .append("reservedEmail", reservedEmail)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("dateOfBirth", dateOfBirth)
                .append("gender", gender)
                .append("blocked", blocked)
                .toString();
    }
}
