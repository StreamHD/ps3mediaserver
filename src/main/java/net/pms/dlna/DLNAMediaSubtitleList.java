/*
 * PS3 Media Server, for streaming any medias to your PS3.
 * Copyright (C) 2008  A.Brochard
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; version 2
 * of the License only.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package net.pms.dlna;

import java.util.ArrayList;

import net.pms.configuration.RendererConfiguration;
import net.pms.dlna.DLNAMediaSubtitle;

/**
 * Contains a collection of DLNAMediaSubtitles. It stores information about
 * the collection. It provides methods for managing the collection and for
 * retrieving/calculating relevant information.
 */
public class DLNAMediaSubtitleList extends ArrayList<DLNAMediaSubtitle> {
	private static final long serialVersionUID = -8788599217445483813L;

	public DLNAMediaSubtitleList() {
		super();
	}

	public DLNAMediaSubtitleList(int i) {
		super(i);
	}

	public boolean add(DLNAMediaSubtitle sub) {
		super.add(sub);
		setSubStatistics(sub.getType(), true);
		return true;
	}

	/**
	 * Use standard getter and setter to access this variable.
	 */
	private boolean hasSubRipSubs = false;

	/**
	 * Use standard getter and setter to access this variable.
	 */
	private boolean hasTextSubs = false;

	/**
	 * Use standard getter and setter to access this variable.
	 */
	private boolean hasMicroDVDSubs = false;

	/**
	 * Use standard getter and setter to access this variable.
	 */
	private boolean hasSamiSubs = false;

	/**
	 * Use standard getter and setter to access this variable.
	 */
	private boolean hasASSSubs = false;

	/**
	 * Use standard getter and setter to access this variable.
	 */
	private boolean hasVobSubs = false;

	/**
	 * Use standard getter and setter to access this variable.
	 */
	private boolean hasEmbeddedSubs = false;

	/**
	 * Sets a flag to indicate whether a subtitle of the specified type
	 * exists in the collection.
	 * @param SubType Type of subtitle
	 * @param hasSubsOfThisType True if the collection contains the type
	 * of subtitle, false otherwise.
	 */
	private void setSubStatistics(int SubType, boolean hasSubsOfThisType) {
		switch(SubType) {
		case DLNAMediaSubtitle.SUBRIP:
			hasSubRipSubs = hasSubsOfThisType;
			return;
		case DLNAMediaSubtitle.TEXT:
			hasTextSubs = hasSubsOfThisType;
			return;
		case DLNAMediaSubtitle.MICRODVD:
			hasMicroDVDSubs = hasSubsOfThisType;
			return;
		case DLNAMediaSubtitle.SAMI:
			hasSamiSubs = hasSubsOfThisType;
			return;
		case DLNAMediaSubtitle.ASS:
			hasASSSubs = hasSubsOfThisType;
			return;
		case DLNAMediaSubtitle.VOBSUB:
			hasVobSubs = hasSubsOfThisType;
			return;
		case DLNAMediaSubtitle.EMBEDDED:
			hasEmbeddedSubs = hasSubsOfThisType;
			return;
		}
		return;
	}

	/**
	 * Indicates if a subtitle of the specified type exists in the collection.
	 * @param SubType Type of subtitle
	 * @return True if the type of subtitle exists in the collection, false otherwise.
	 */
	public boolean hasSubsToHandle(int SubType) {
		switch(SubType) {
		case DLNAMediaSubtitle.SUBRIP:
			return hasSubRipSubs;
		case DLNAMediaSubtitle.TEXT:
			return hasTextSubs;
		case DLNAMediaSubtitle.MICRODVD:
			return hasMicroDVDSubs;
		case DLNAMediaSubtitle.SAMI:
			return hasSamiSubs;
		case DLNAMediaSubtitle.ASS:
			return hasASSSubs;
		case DLNAMediaSubtitle.VOBSUB:
			return hasVobSubs;
		case DLNAMediaSubtitle.EMBEDDED:
			return hasEmbeddedSubs;
		}
		return false;
	}

	/**
	 * Indicates if there are any subtitles contained in the collection.
	 * @return True if the collection contains subtitles, false otherwise.
	 */
	public boolean hasSubsToHandle() {
		for (int SubType : DLNAMediaSubtitle.subTypes) {
			if (hasSubsToHandle(SubType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Indicates if there are any external subtitles contained in the collection.
	 * @return True if the collection contains external subtitles, false otherwise.
	 */
	public boolean hasExternalSubsToHandle() {
		for (int SubType : DLNAMediaSubtitle.subTypes) {
			if (SubType != DLNAMediaSubtitle.EMBEDDED && hasSubsToHandle(SubType)) {
				return true;
			}
		}
		return false;

	}

	/**
	 * Indicates if there are any embedded subtitles contained in the collection.
	 * @return True if the collection contains embedded subtitles, false otherwise.
	 */
	public boolean hasEmbeddedSubsToHandle() {
		return hasEmbeddedSubs;
	}

	/**
	 * Indicates if the specified type of subtitle is available and natively supported
	 * by the renderer.
	 * @param SubType Type of subtitle
	 * @param renderer The renderer to check
	 * @return True if the specified type of subtitle is available and natively supported
	 * by the renderer, false otherwise.
	 */
	public boolean hasStreamableSubs(int SubType, RendererConfiguration renderer) {
		switch(SubType) {
		case DLNAMediaSubtitle.SUBRIP:
			return hasSubRipSubs && renderer.isSubSupported(SubType);
		case DLNAMediaSubtitle.TEXT:
			return hasTextSubs && renderer.isSubSupported(SubType);
		case DLNAMediaSubtitle.MICRODVD:
			return hasMicroDVDSubs && renderer.isSubSupported(SubType);
		case DLNAMediaSubtitle.SAMI:
			return hasSamiSubs && renderer.isSubSupported(SubType);
		case DLNAMediaSubtitle.ASS:
			return hasASSSubs && renderer.isSubSupported(SubType);
		case DLNAMediaSubtitle.VOBSUB:
			return hasVobSubs && renderer.isSubSupported(SubType);
		case DLNAMediaSubtitle.EMBEDDED:
			return hasEmbeddedSubs && renderer.isSubSupported(SubType);
		}
		return false;
	}

	/**
	 * Indicates if there are any subtitles available which are natively supported
	 * by the renderer.
	 * @param renderer The renderer to check
	 * @return True if there are subtitles available which are natively supported by
	 * the renderer, false otherwise.
	 */
	public boolean hasStreamableSubs(RendererConfiguration renderer) {
		for (int SubType : DLNAMediaSubtitle.subTypes) {
			if (hasSubsToHandle(SubType) && renderer.isSubSupported(SubType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Indicates if there are any external subtitles available which are natively supported
	 * by the renderer.
	 * @param renderer The renderer to check
	 * @return True if there are external subtitles available which are natively supported
	 * by the renderer, false otherwise.
	 */
	public boolean hasStreamableExternalSubs(RendererConfiguration renderer) {
		for (int SubType : DLNAMediaSubtitle.subTypes) {
			if (SubType != DLNAMediaSubtitle.EMBEDDED &&
					hasSubsToHandle(SubType) && renderer.isSubSupported(SubType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Indicates if there are any embedded subtitles available which are natively supported
	 * by the renderer.
	 * @param renderer The renderer to check
	 * @return True if there are embedded subtitles available which are natively supported
	 * by the renderer, false otherwise.
	 */
	public boolean hasStreamableEmbeddedSubs(RendererConfiguration renderer) {
		return hasEmbeddedSubs && renderer.isSubSupported(DLNAMediaSubtitle.EMBEDDED);
	}

	/**
	 * Indicates if there are subs of the specified type available which should be streamed
	 * to the renderer during remuxing/transcoding of video.
	 * @param SubType Type of subtitle
	 * @param renderer The renderer to check
	 * @return True if there are subtitles of the specified type which should be streamed,
	 * false otherwise.
	 */
	public boolean hasSkipTranscodeSubs(int SubType, RendererConfiguration renderer) {
		return hasSubsToHandle(SubType) && renderer.isSubSkipTranscode(SubType);
	}

	/**
	 * Indicates if there are any subs available which should be streamed to the renderer
	 * during remuxing/transcoding of video.
	 * @param renderer The renderer to check
	 * @return True if there are subtitles which should be streamed during transcoding of
	 * video, false otherwise.
	 */
	public boolean hasSkipTranscodeSubs(RendererConfiguration renderer) {
		for (int SubType : DLNAMediaSubtitle.subTypes) {
			if (hasSubsToHandle(SubType) && renderer.isSubSkipTranscode(SubType)) {
				return true;
			}
		}
		return false;
	}
}
