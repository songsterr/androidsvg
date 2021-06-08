/*
   Copyright 2017 Paul LeBeau, Cave Rock Software Ltd.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package com.caverock.androidsvg;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Build;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadow.api.Shadow;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE, sdk=Build.VERSION_CODES.O, shadows={MockCanvas.class, MockPath.class, MockPaint.class})
public class FontFeatureSettingsTest
{
   @Test
   public void fontFeatures() throws SVGParseException
   {
      String  test = "<svg>\n" +
                     "  <text style=\"font-feature-settings: 'liga' 0, 'clig', 'pnum' on, 'swsh' 42\">Test</text>\n" +
                     "</svg>";
      SVG  svg = SVG.getFromString(test);

      Bitmap bm1 = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
      Canvas canvas = new Canvas(bm1);
      svg.renderToCanvas(canvas);

      MockCanvas    mock = ((MockCanvas) Shadow.extract(canvas));

      //List<String>  ops = ((MockCanvas) Shadow.extract(canvas)).getOperations();
      //System.out.println(String.join(",", ops));
      String ff = mock.paintProp(3, "ff") + ',';
      assertTrue(ff.contains("'pnum' 1,"));
      assertTrue(ff.contains("'clig' 1,"));
      assertTrue(ff.contains("'swsh' 42,"));
      assertTrue(ff.contains("'liga' 0,"));
   }

   //-----------------------------------------------------------------------------------------------

   @Test
   @Ignore("Font variation disabled because it's slow")
   public void fontVariation() throws SVGParseException
   {
      String  test = "<svg>\n" +
                     "  <text style=\"font-variation-settings: 'wght' 100, 'slnt' -14\">Test</text>\n" +
                     "</svg>";
      SVG  svg = SVG.getFromString(test);

      Bitmap bm1 = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
      Canvas canvas = new Canvas(bm1);
      svg.renderToCanvas(canvas);

      MockCanvas    mock = ((MockCanvas) Shadow.extract(canvas));

      //List<String>  ops = ((MockCanvas) Shadow.extract(canvas)).getOperations();
      //System.out.println(String.join(",", ops));
      String paintProp = mock.paintProp(3, "fv");
      assertTrue(paintProp.contains("'wdth' 100"));
      assertTrue(paintProp.contains("'slnt' -14"));
   }

   //-----------------------------------------------------------------------------------------------

   @Test
   @Ignore("Font variation disabled because it's slow")
   public void fontStretch() throws SVGParseException
   {
      String  test = "<svg>\n" +
                     "  <text style=\"font-stretch: ultra-condensed\">Test\n" +
                     "  <tspan style=\"font-stretch: normal\">Test</tspan></text>\n" +
                     "  <text style=\"font-stretch: ultra-expanded\">Test</text>\n" +
                     "  <text style=\"font-stretch: 80%\">Test</text>\n" +
                     "  <text style=\"font-stretch: 66\">Test</text>\n" +
                     "  <text style=\"font-stretch: -10%\">Test</text>\n" +
                     "</svg>";
      SVG  svg = SVG.getFromString(test);

      Bitmap bm1 = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
      Canvas canvas = new Canvas(bm1);
      svg.renderToCanvas(canvas);

      MockCanvas    mock = ((MockCanvas) Shadow.extract(canvas));

      //List<String>  ops = ((MockCanvas) Shadow.extract(canvas)).getOperations();
      //System.out.println(String.join(",", ops));
      assertEquals("'wdth' 50,'wght' 400", mock.paintProp(5, "fv"));
      assertEquals("'wdth' 100,'wght' 400", mock.paintProp(7, "fv"));
      assertEquals("'wdth' 200,'wght' 400", mock.paintProp(11, "fv"));
      assertEquals("'wdth' 80,'wght' 400", mock.paintProp(14, "fv"));
      assertEquals("'wdth' 100,'wght' 400", mock.paintProp(17, "fv"));
      assertEquals("'wdth' 100,'wght' 400", mock.paintProp(20, "fv"));
   }

   //-----------------------------------------------------------------------------------------------

}
