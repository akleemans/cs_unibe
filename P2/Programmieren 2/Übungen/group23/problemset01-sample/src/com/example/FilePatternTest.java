package com.example;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileFilter;

import org.junit.Test;

public class FilePatternTest {
    
    protected FileFilter newFileFilter(String string) {
        return new FilePattern(string);
    }
    
    @Test public void fnameShouldMatchFname() {
        assertTrue(newFileFilter("fname").accept(new File("fname")));
    }


    @Test public void fnameShouldNotMatchF() {
        assertFalse(newFileFilter("fname").accept(new File("f")));
    }

    @Test public void fnameShouldNotMatchFnameDotTxt() {
        assertFalse(newFileFilter("fname").accept(new File("fname.txt")));
    }

    @Test public void starShouldMatch() {
        assertTrue(newFileFilter("*").accept(new File("")));
    }

    @Test public void starShouldMatchF() {
        assertTrue(newFileFilter("*").accept(new File("f")));
    }

    @Test public void starShouldMatchFname() {
        assertTrue(newFileFilter("*").accept(new File("fname")));
    }

    @Test public void starShouldMatchFnameDotTxt() {
        assertTrue(newFileFilter("*").accept(new File("fname.txt")));
    }

    @Test public void markShouldNotMatch() {
        assertFalse(newFileFilter("?").accept(new File("")));
    }

    @Test public void markShouldMatchF() {
        assertTrue(newFileFilter("?").accept(new File("f")));
    }

    @Test public void markShouldNotMatchFname() {
        assertFalse(newFileFilter("?").accept(new File("fname")));
    }

    @Test public void markShouldNotMatchFnameDotTxt() {
        assertFalse(newFileFilter("?").accept(new File("fname.txt")));
    }

    @Test public void markMarkMarkShouldNotMatchFo() {
        assertFalse(newFileFilter("???").accept(new File("fo")));
    }

    @Test public void markMarkMarkShouldMatchFoo() {
        assertTrue(newFileFilter("???").accept(new File("foo")));
    }

    @Test public void markMarkMarkShouldNotMatchFooo() {
        assertFalse(newFileFilter("???").accept(new File("fooo")));
    }

    @Test public void starDotTxtShouldNotMatch() {
        assertFalse(newFileFilter("*.txt").accept(new File("")));
    }

    @Test public void starDotTxtShouldMatchDotTxt() {
        assertTrue(newFileFilter("*.txt").accept(new File(".txt")));
    }

    @Test public void starDotTxtShouldMatchFDotTxt() {
        assertTrue(newFileFilter("*.txt").accept(new File("f.txt")));
    }

    @Test public void starDotTxtShouldNotMatchFnameDotTx() {
        assertFalse(newFileFilter("*.txt").accept(new File("fname.tx")));
    }

    @Test public void starDotTxtShouldMatchFnameDotTxt() {
        assertTrue(newFileFilter("*.txt").accept(new File("fname.txt")));
    }

    @Test public void starDotTxtShouldNotMatchFnametxt() {
        assertFalse(newFileFilter("*.txt").accept(new File("fnametxt")));
    }

    @Test public void starDotTxtShouldNotMatchFnameDotTxtPlus() {
        assertFalse(newFileFilter("*.txt").accept(new File("fname.txt+")));
    }

    @Test public void fnameStarShouldNotMatch() {
        assertFalse(newFileFilter("fname*").accept(new File("")));
    }

    @Test public void fnameStarShouldMatchFname() {
        assertTrue(newFileFilter("fname*").accept(new File("fname")));
    }

    @Test public void fnameStarShouldMatchFnameDotTxt() {
        assertTrue(newFileFilter("fname*").accept(new File("fname.txt")));
    }

    @Test public void fnameStarShouldMatchFnameDotTx() {
        assertTrue(newFileFilter("fname*").accept(new File("fname.tx")));
    }

    @Test public void fnameStarShouldNotMatchFnamDotTxt() {
        assertFalse(newFileFilter("fname*").accept(new File("fnam.txt")));
    }

    @Test public void fnameStarShouldMatchFnamePlusDotTxt() {
        assertTrue(newFileFilter("fname*").accept(new File("fname+.txt")));
    }

    @Test public void fStarDotTxtShouldMatchFDotTxt() {
        assertTrue(newFileFilter("f*.txt").accept(new File("f.txt")));
    }

    @Test public void fStarDotTxtShouldMatchFnameDotTxt() {
        assertTrue(newFileFilter("f*.txt").accept(new File("fname.txt")));
    }

    @Test public void fStarDotTxtShouldNotMatchNameDotTxt() {
        assertFalse(newFileFilter("f*.txt").accept(new File("name.txt")));
    }

    @Test public void fStarDotTxtShouldNotMatchFnametxt() {
        assertFalse(newFileFilter("f*.txt").accept(new File("fnametxt")));
    }

    @Test public void markDotTxtShouldNotMatchDotTxt() {
        assertFalse(newFileFilter("?.txt").accept(new File(".txt")));
    }

    @Test public void markDotTxtShouldMatchFDotTxt() {
        assertTrue(newFileFilter("?.txt").accept(new File("f.txt")));
    }

    @Test public void markDotTxtShouldNotMatchFnameDotTxt() {
        assertFalse(newFileFilter("?.txt").accept(new File("fname.txt")));
    }

    @Test public void fnameDotMarkShouldNotMatchFnameDot() {
        assertFalse(newFileFilter("fname.?").accept(new File("fname.")));
    }

    @Test public void fnameDotMarkShouldMatchFnameDotT() {
        assertTrue(newFileFilter("fname.?").accept(new File("fname.t")));
    }

    @Test public void fnameDotMarkShouldNotMatchFnameDotTxt() {
        assertFalse(newFileFilter("fname.?").accept(new File("fname.txt")));
    }

    @Test public void fMarkDotTxtShouldNotMatchFDotTxt() {
        assertFalse(newFileFilter("f?.txt").accept(new File("f.txt")));
    }

    @Test public void fMarkDotTxtShouldMatchFfDotTxt() {
        assertTrue(newFileFilter("f?.txt").accept(new File("ff.txt")));
    }

    @Test public void fMarkDotTxtShouldNotMatchFnameDotTxt() {
        assertFalse(newFileFilter("f?.txt").accept(new File("fname.txt")));
    }

    @Test public void fMarkDotTxtShouldNotMatchFtxt() {
        assertFalse(newFileFilter("f?.txt").accept(new File("ftxt")));
    }

    /*
     * New tests
     */
    @Test public void fMarkDotTxtShouldNotMatchDotTxt() {
        assertFalse(newFileFilter("f?.txt").accept(new File(".txt")));
    }

    @Test public void fStarDotStarShouldMatchFnameDotTxt() {
        assertTrue(newFileFilter("*.*").accept(new File("fname.txt")));
    }

    @Test public void fStarStarShouldMatchEmptyString() {
        assertTrue(newFileFilter("**").accept(new File("")));
    }

    @Test public void fStarMarkShouldNotMatchEmptyString() {
        assertFalse(newFileFilter("*?").accept(new File("")));
    }

    @Test public void fStarMarkStarShouldNotMatchEmptyString() {
        assertFalse(newFileFilter("*?*").accept(new File("")));
    }

    @Test public void fStarMarkShouldMatchX() {
        assertTrue(newFileFilter("*?").accept(new File("x")));
    }

    @Test public void fMarkStarShouldMatchX() {
        assertTrue(newFileFilter("?*").accept(new File("x")));
    }

}
