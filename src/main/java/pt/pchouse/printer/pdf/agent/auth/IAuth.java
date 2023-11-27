/*
 *  Copyright (C) 2022  PChouse - Reflexão Estudos e Sistemas Informáticos, lda
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package pt.pchouse.printer.pdf.agent.auth;
/*
 * Copyright 2023 PChouse - Reflexão, Estudos e Sistemas Informáricos, Lda
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @since 1.0.0
 */
@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public interface IAuth
{

    /**
     * Get if teh request client is authorized or not
     *
     * @return If authorized
     * @since 1.0.0
     */
    boolean isNotAuthorized();

    /**
     * Catch the remote IP from the web container
     *
     * @since 1.0.0
     */
    void catchRemoteIP();
}
