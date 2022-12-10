package com.date.tingting.domain.partyGroup;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartyGroupRepository extends JpaRepository<PartyGroup, Long> {

    Optional<PartyGroup> findByHostAndGuest(String host, String guest);
}
