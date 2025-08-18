package team3.sambakja.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StartupResponse(
    @JsonProperty("id")
    Long id,

    @JsonProperty("pbanc_sn")
    String pbancSn, // 공고일련번호

    @JsonProperty("biz_pbanc_nm")
    String bizPbancNm, // 사업공고명

    @JsonProperty("pbanc_ntrp_nm")
    String pbancNtrpNm, // 공고기업명

    @JsonProperty("intg_pbanc_biz_nm")
    String intgPbancBizNm, // 통합공고사업명

    @JsonProperty("pbanc_ctnt")
    String pbancCtnt, // 공고내용

    @JsonProperty("aply_trgt")
    String aplyTrgt, // 신청대상

    @JsonProperty("aply_trgt_ctnt")
    String aplyTrgtCtnt, // 신청대상내용

    @JsonProperty("biz_trgt_age")
    String bizTrgtAge, // 사업대상연령

    @JsonProperty("biz_enyy")
    String bizEnyy, // 사업영업년수

    @JsonProperty("supt_regin")
    String suptRegin, // 지원지역

    @JsonProperty("supt_biz_clsfc")
    String suptBizClsfc, // 지원사업분류

    @JsonProperty("sprv_inst")
    String sprvInst, // 주관기관

    @JsonProperty("biz_prch_dprt_nm")
    String bizPrchDprtNm, // 사업주관부서명

    @JsonProperty("prch_cnpl_no")
    String prchCnplNo, // 주관연락처번호

    @JsonProperty("pbanc_rcpt_bgng_dt")
    String pbancRcptBgngDt, // 공고접수시작일

    @JsonProperty("pbanc_rcpt_end_dt")
    String pbancRcptEndDt, // 공고접수종료일

    @JsonProperty("detl_pg_url")
    String detlPgUrl, // 상세페이지URL

    @JsonProperty("biz_gdnc_url")
    String bizGdncUrl, // 사업안내URL

    @JsonProperty("biz_aply_url")
    String bizAplyUrl, // 사업신청URL

    @JsonProperty("aply_mthd_onli_rcpt_istc")
    String aplyMthdOnliRcptIstc, // 신청방법온라인접수여부

    @JsonProperty("aply_mthd_eml_rcpt_istc")
    String aplyMthdEmlRcptIstc, // 신청방법이메일접수여부

    @JsonProperty("aply_mthd_fax_rcpt_istc")
    String aplyMthdFaxRcptIstc, // 신청방법팩스접수여부

    @JsonProperty("aply_mthd_pssr_rcpt_istc")
    String aplyMthdPssrRcptIstc, // 신청방법우편접수여부

    @JsonProperty("aply_mthd_vst_rcpt_istc")
    String aplyMthdVstRcptIstc, // 신청방법방문접수여부

    @JsonProperty("aply_mthd_etc_istc")
    String aplyMthdEtcIstc, // 신청방법기타여부

    @JsonProperty("aply_excl_trgt_ctnt")
    String aplyExclTrgtCtnt, // 신청제외대상내용

    @JsonProperty("intg_pbanc_yn")
    String intgPbancYn, // 통합공고여부

    @JsonProperty("rcrt_prgs_yn")
    String rcrtPrgsYn, // 모집진행여부

    @JsonProperty("prfn_matr")
    String prfnMatr // 우대사항
) {}
