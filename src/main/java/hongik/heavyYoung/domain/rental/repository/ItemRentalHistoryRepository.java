package hongik.heavyYoung.domain.rental.repository;

import hongik.heavyYoung.domain.member.entity.Member;
import hongik.heavyYoung.domain.rental.entity.ItemRentalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRentalHistoryRepository extends JpaRepository<ItemRentalHistory, Long> {
    @Query("""
           select h
           from ItemRentalHistory h
           join fetch h.item i
           join fetch i.itemCategory c
           where h.member = :member
             and h.returnedAt is null
           """)
    List<ItemRentalHistory> findCurrentWithItemAndCategory(@Param("member") Member member);
}
