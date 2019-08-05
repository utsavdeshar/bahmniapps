
package org.bahmni.insurance.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "uuid",
    "name",
    "dataType",
    "shortName",
    "conceptClass",
    "hiNormal",
    "lowNormal",
    "set",
    "mappings"
})
public class CodedAnswer {

    @JsonProperty("uuid")
    private String uuid;
    @JsonProperty("name")
    private String name;
    @JsonProperty("dataType")
    private String dataType;
    @JsonProperty("shortName")
    private String shortName;
    @JsonProperty("conceptClass")
    private String conceptClass;
    @JsonProperty("hiNormal")
    private Object hiNormal;
    @JsonProperty("lowNormal")
    private Object lowNormal;
    @JsonProperty("set")
    private Boolean set;
    @JsonProperty("mappings")
    private List<Mapping> mappings = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("uuid")
    public String getUuid() {
        return uuid;
    }

    @JsonProperty("uuid")
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("dataType")
    public String getDataType() {
        return dataType;
    }

    @JsonProperty("dataType")
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @JsonProperty("shortName")
    public String getShortName() {
        return shortName;
    }

    @JsonProperty("shortName")
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @JsonProperty("conceptClass")
    public String getConceptClass() {
        return conceptClass;
    }

    @JsonProperty("conceptClass")
    public void setConceptClass(String conceptClass) {
        this.conceptClass = conceptClass;
    }

    @JsonProperty("hiNormal")
    public Object getHiNormal() {
        return hiNormal;
    }

    @JsonProperty("hiNormal")
    public void setHiNormal(Object hiNormal) {
        this.hiNormal = hiNormal;
    }

    @JsonProperty("lowNormal")
    public Object getLowNormal() {
        return lowNormal;
    }

    @JsonProperty("lowNormal")
    public void setLowNormal(Object lowNormal) {
        this.lowNormal = lowNormal;
    }

    @JsonProperty("set")
    public Boolean getSet() {
        return set;
    }

    @JsonProperty("set")
    public void setSet(Boolean set) {
        this.set = set;
    }

    @JsonProperty("mappings")
    public List<Mapping> getMappings() {
        return mappings;
    }

    @JsonProperty("mappings")
    public void setMappings(List<Mapping> mappings) {
        this.mappings = mappings;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
