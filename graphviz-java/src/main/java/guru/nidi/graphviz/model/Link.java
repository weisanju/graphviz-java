/*
 * Copyright © 2015 Stefan Niederhauser (nidin@gmx.ch)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package guru.nidi.graphviz.model;

import guru.nidi.graphviz.attribute.*;

import javax.annotation.Nullable;

public final class Link implements Attributed<Link>, LinkTarget {
    @Nullable
    final LinkSource from;
    final LinkTarget to;
    final MutableAttributed<Link> attributes;

    public static Link to(MutableNode node) {
        return to(node.withRecord(null));
    }

    public static Link to(Node node) {
        return to(node.port());
    }

    public static Link to(LinkTarget to) {
        return objBetween(null, to);
    }

    public LinkTarget to() {
        return to;
    }

    public static Link between(Node from, Node to) {
        return between(from.port(), to.port());
    }

    public static Link between(LinkSource from, LinkTarget to) {
        return objBetween(from, to);
    }

    private static Link objBetween(@Nullable LinkSource from, LinkTarget to) {
        return CreationContext.createLink(from, to);
    }

    Link(@Nullable LinkSource from, LinkTarget to, Attributes attributes) {
        this.from = from;
        this.to = to;
        this.attributes = new SimpleMutableAttributed<>(this, attributes);
    }

    public Link with(Attributes attrs) {
        return new Link(from, to, attrs.applyTo(attributes.applyTo(Attributes.attrs())));
    }

    @Override
    public Attributes applyTo(MapAttributes attrs) {
        return attributes.applyTo(attrs);
    }

    @Override
    public Link linkTo() {
        return this;
    }

    @Override
    public Linkable asLinkable() {
        return to.asLinkable();
    }

    @Nullable
    public LinkSource from() {
        return from;
    }

    //TODO differentiate between mutable and immutable
    public MutableAttributed<Link> attrs() {
        return attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Link link = (Link) o;

        /*
        //including from could cause circular executions
//        if (from != null ? !from.equals(addLink.from) : addLink.from != null) {
//            return false;
//        }
//        if (to != null ? !to.equals(link.to) : link.to != null) {
//            return false;
//        }
        */
        return attributes.equals(link.attributes);
    }

    @Override
    public int hashCode() {
        //including from could cause circular executions
        int result = 0;// from != null ? from.hashCode() : 0;
        //result = 31 * result + (to != null ? to.hashCode() : 0);
        result = 31 * result + attributes.hashCode();
        return result;
    }
}
