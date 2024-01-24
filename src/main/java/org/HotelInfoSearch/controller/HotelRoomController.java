package org.HotelInfoSearch.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.HotelInfoSearch.util.IdGenerator;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


@RestController
public class HotelRoomController {

    /* 객실 정보를 조회하는 REST-API 설계 */
    /* REST-API 요청
       GET /hotels/{hotelId}/rooms/{roomNumber}?fromDate={yyyyMMdd}&toDate={yyyMMdd}
       - hotelId : (필수) Long 타입이며, 호텔의 고유 아이디 값
       - roomNumber : (필수) String 타입이며, 호텔 리소스에 포함된 룸 중 고유한 아이디 값
       - fromDate, toDate : (선택) String 타입이며, yyyyMMdd 형식의 예약일 */
    /* REST-API 응답
       {
          "id": "1201928183",
          "roomNumber": "West-Wing-3928",
          "numberOfBeds": 2,
          "roomType": "deluxe",
          "originalPrice": "150.00",
          "reservations": [
            {
              "id" : "129171201928183",
              "reservedDate": "{yyyy-MM-dd}"
            },{
              "id": "12171201928183",
              "reservedDate": "{yyyy-MM-dd}"
             } ]
            },*/
    @GetMapping(path = "/hotels/{hotelId}/rooms/{roomNumber}")
    public HotelRoomResponse getHotelRoomByPeriod(
            @PathVariable Long hotelId,
            @PathVariable String roomNumber,
            @RequestParam(value = "fromDate", required = false) @DateTimeFormat(pattern = "yyyyMMdd") LocalDate fromDate,
            @RequestParam(value = "toDate", required = false) @DateTimeFormat(pattern = "yyyyMMdd") LocalDate toDate) {

        Long hotelRoomId = IdGenerator.create();
        BigDecimal originalPrice = new BigDecimal("130.00");

        HotelRoomResponse response = HotelRoomResponse.of(hotelRoomId, roomNumber, HotelRoomType.DOUBLE, originalPrice);
        if(Objects.nonNull(fromDate) && Objects.nonNull(toDate)) fromDate.datesUntil(toDate.plusDays(1)).forEach(date -> response.reservedAt(date));

        return response;
    }


    /* 호텔 객실 데이터를 생성하는 REST-API 설계 명세서 */
    /* REST-API 요청
       POST /hotels/{hotelId}/rooms
       {
            "roomNumber" : "West-Wing-3928",
             "roomType" : "double",
             "originalPrice" : "150.00"
       }
       REST-API 응답
       - [HEADER] X-CREATED-AT : yyyy-MM-dd'T'HH:mm:ssXXX
       {
            "id" : "1201928183"
       }
      */

    private static final String HEADER_CREATED_AT = "X-CREATED-AT";
    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");

    @PostMapping(path = "/hotels/{hotelId}/rooms")
    public ResponseEntity<HotelRoomIdResponse> createHotelRoom(
            @PathVariable Long hotelId,
            @RequestBody HotelRoomRequest hotelRoomRequest) {
        System.out.println(hotelRoomRequest.toString());

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HEADER_CREATED_AT, DATE_FORMATTER.format(ZonedDateTime.now()));
        HotelRoomIdResponse body = HotelRoomIdResponse.from(1_002_003_004L);

        return new ResponseEntity(body, headers, HttpStatus.OK);
    }

    /* 호텔 객실 삭제하는 API 명세서 */
    /* REST-API 요청
       DELETE /hotels/{hotelId}/rooms/{roomNumber}
       - hotelId : (필수) Long 타입이며, 호텔의 고유 아이디 값
       - roomNumber : (필수) String 타입이며, 호텔 리소스에 포함된 객실 중에서 고유한 아이디 값

       REST-API 응답
       {
            "isSuccess" : true,
            "message" : "success"
       }
       */

    @DeleteMapping(path = "/hotels/{hotelId}/rooms/{roomNumber}")
    public DeleteResultResponse deleteHotelRoom(
            @PathVariable Long hotelId,
            @PathVariable String roomNumber) {
        System.out.println("Delete Request. hotelId=" + hotelId + ", roomNumber=" + roomNumber);

        return new DeleteResultResponse(Boolean.TRUE, "success");
    }


}
