package pl.nataliabratek.project.data.comments;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="property_comment")
@NoArgsConstructor
@AllArgsConstructor
public class PropertyCommentEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="user_id")
    private Integer userId;
    @Column(name = "property_id")
    private Integer propertyId;
    @Column(name="message")
    private String message;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
