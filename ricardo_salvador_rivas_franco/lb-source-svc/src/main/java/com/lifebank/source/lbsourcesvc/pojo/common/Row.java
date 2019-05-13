
package com.lifebank.source.lbsourcesvc.pojo.common;

import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "title",
    "url",
    "mainText",
    "subText"
})
public class Row implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("title")
    private String title;
    @JsonProperty("url")
    private String url;
    @JsonProperty("mainText")
    private String mainText;
    @JsonProperty("subText")
    private String subText;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("mainText")
    public String getMainText() {
        return mainText;
    }

    @JsonProperty("mainText")
    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    @JsonProperty("subText")
    public String getSubText() {
        return subText;
    }

    @JsonProperty("subText")
    public void setSubText(String subText) {
        this.subText = subText;
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
