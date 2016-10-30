package io.github.anxolerd.jee.model;


import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sinner")
@Data
@EqualsAndHashCode(exclude = {"accuses", "execution"})
@ToString(exclude = {"accuses", "execution"})
@NoArgsConstructor
@AllArgsConstructor
public class Sinner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @Column(name = "first_name", nullable = false)
    String firstName;

    @Column(name = "last_name", nullable = false)
    String lastName;

    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date", nullable = false)
    Date birthDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "death_date")
    Date deathDate;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "sinner")
    List<Accuse> accuses;

    @OneToOne(mappedBy = "sinner")
    Execution execution;
}
