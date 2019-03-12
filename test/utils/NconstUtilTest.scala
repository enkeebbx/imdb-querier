package utils

import helpers.LunaTestSpecification

class NconstUtilTest extends LunaTestSpecification {

  "NconstUtilTest" should {
    "pad, unpad test" in new WithMemDb {
      val padded = NconstUtil.padNconst("102")
      val unpadded = NconstUtil.unpadNconst("nm0000102")

      assert(padded == "nm0000102")
      assert(unpadded == "102")
    }
  }
}
