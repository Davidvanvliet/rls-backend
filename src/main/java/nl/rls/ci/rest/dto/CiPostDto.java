package nl.rls.ci.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class CiPostDto {
    private String messageXml;
    private String messageId;
    private Date createDate;
    private Date postDate;
    private boolean posted;
}

