package com.date.tingting.domain.code;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface CodeRepository extends JpaRepository<Code, String> {

    List<Code> findAllByCodeGroup(String codeGroup);

}
