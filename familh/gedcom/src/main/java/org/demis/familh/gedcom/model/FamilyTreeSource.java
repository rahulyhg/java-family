package org.demis.familh.gedcom.model;

public class FamilyTreeSource {

    private Integer familyTreeSourceId;

    private String approvedSystemId;

    private String version;

    private String name;

    private FamilyTreeSourceData data;

    private FamilyTreeSourceCorporation corporation;

    public FamilyTreeSource() {

    }

    public Integer getFamilyTreeSourceId() {
        return familyTreeSourceId;
    }

    public void setFamilyTreeSourceId(Integer familyTreeSourceId) {
        this.familyTreeSourceId = familyTreeSourceId;
    }

    public String getApprovedSystemId() {
        return approvedSystemId;
    }

    public void setApprovedSystemId(String approvedSystemId) {
        this.approvedSystemId = approvedSystemId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FamilyTreeSourceCorporation getCorporation() {
        return corporation;
    }

    public void setCorporation(FamilyTreeSourceCorporation corporation) {
        this.corporation = corporation;
    }

    public FamilyTreeSourceData getData() {
        return data;
    }

    public void setData(FamilyTreeSourceData data) {
        this.data = data;
    }
}
