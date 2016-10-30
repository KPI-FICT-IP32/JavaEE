package io.github.anxolerd.jee.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "execution")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Execution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @OneToOne
    @JoinColumn(name = "sinner_id", referencedColumnName = "id", nullable = false)
    Sinner sinner;

    @Column(name = "description", nullable = false)
    String description;

    @Column(name = "place")
    String place;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    Date date;
}
