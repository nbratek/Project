package pl.nataliabratek.project.data.notification;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.nataliabratek.project.domain.model.PropertyType;

@Entity
@Getter
@Setter
@Table(name="property_subscription")
@NoArgsConstructor
@AllArgsConstructor
public class PropertySubscriptionEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "location")
    private String location;
    @Column(name="property_type")
    private PropertyType propertyType;
    @Column(name="user_id")
    private Integer userId;
}
