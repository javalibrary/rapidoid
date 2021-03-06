/*-
 * #%L
 * rapidoid-rest
 * %%
 * Copyright (C) 2014 - 2020 Nikolche Mihajlovski and contributors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package org.rapidoid.http;

import org.junit.jupiter.api.Test;
import org.rapidoid.annotation.Authors;
import org.rapidoid.annotation.GET;
import org.rapidoid.annotation.POST;
import org.rapidoid.annotation.Since;
import org.rapidoid.setup.App;
import org.rapidoid.setup.Apps;

@Authors("Nikolche Mihajlovski")
@Since("5.0.10")
public class HttpPojoControllerTest extends IsolatedIntegrationTest {

    @SuppressWarnings("unchecked")
    @Test
    public void testPojoHandlers() {
        App app = new App();

        app.beans(new Object() {

            @GET(uri = "/a")
            public Object theFoo() {
                return "foo";
            }

            @POST(uri = "/x")
            public Object x(Req req, Resp resp) {
                return "x";
            }
        });

        onlyGet("/a");
        onlyPost("/x");
        notFound("/b");

        app.beans(new Ctrl1());

        onlyGet("/a");
        onlyGet("/b");
        onlyPost("/x");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testPojoHandlersWithIoC() {
        notFound("/b");

        App app = new App();
        app.beans(new Ctrl1());

        onlyGet("/b");
        onlyGet("/x");
        notFound("/x");
    }

}

@interface MyTestController {
}

@MyTestController
class Ctrl1 {

    @GET("/b")
    public Object bbb() {
        return "bar";
    }

}
