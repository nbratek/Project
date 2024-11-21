package pl.nataliabratek.project.data.favorites;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="property_favorites")
@NoArgsConstructor
@AllArgsConstructor
public class PropertyFavoritesEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "property_id")
    private Integer propertyId;
    @Column(name="user_id")
    private Integer userId;

}
