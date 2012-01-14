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

package net.pms.test.formats;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import net.pms.configuration.RendererConfiguration;
import net.pms.dlna.DLNAMediaAudio;
import net.pms.dlna.DLNAMediaInfo;
import net.pms.formats.DVRMS;
import net.pms.formats.FLAC;
import net.pms.formats.Format;
import net.pms.formats.GIF;
import net.pms.formats.ISO;
import net.pms.formats.JPG;
import net.pms.formats.M4A;
import net.pms.formats.MKV;
import net.pms.formats.MP3;
import net.pms.formats.MPG;
import net.pms.formats.OGG;
import net.pms.formats.PNG;
import net.pms.formats.RAW;
import net.pms.formats.TIF;
import net.pms.formats.WAV;
import net.pms.formats.WEB;
import net.pms.network.HTTPResource;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;


/**
 * Test the recognition of formats.
 */
public class FormatRecognitionTest {
	@Before
	public void setUp() {
		// Silence all log messages from the PMS code that is being tested
		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.reset(); 

		// Initialize the RendererConfiguration
		RendererConfiguration.loadRendererConfigurations();
	}

    /**
     * Test some basic functionality of {@link RendererConfiguration#isCompatible(DLNAMediaInfo, Format)}
     */
    @Test
	public void testRendererConfigurationBasics() {
		RendererConfiguration conf = RendererConfiguration.getRendererConfigurationByName("Playstation 3");
		assertNotNull("No renderer named \"Playstation 3\" found.", conf);
		assertEquals("With nothing provided isCompatible() should return false", false,
				conf.isCompatible(null, null));
	}

	/**
	 * Test the compatibility of the Playstation 3 with the GIF format.
	 */
	@Test
	public void testPlaystationImageGifCompatibility() {
		RendererConfiguration conf = RendererConfiguration.getRendererConfigurationByName("Playstation 3");
		assertNotNull("No renderer named \"Playstation 3\" found.", conf);

		// Construct GIF information
		DLNAMediaInfo info = new DLNAMediaInfo();
		info.setContainer("gif");
		Format format = new GIF();
		format.match("test.gif");
		assertEquals("PS3 is reported to be incompatible with GIF", true,
				conf.isCompatible(info, format));
	}

	/**
	 * Test the compatibility of the Playstation 3 with the JPG format.
	 */
	@Test
	public void testPlaystationImageJpgCompatibility() {
		RendererConfiguration conf = RendererConfiguration.getRendererConfigurationByName("Playstation 3");
		assertNotNull("No renderer named \"Playstation 3\" found.", conf);

		// Construct JPG information
		DLNAMediaInfo info = new DLNAMediaInfo();
		info.setContainer("jpg");
		Format format = new JPG();
		format.match("test.jpeg");
		assertEquals("PS3 is reported to be incompatible with JPG", true,
				conf.isCompatible(info, format));
	}


	/**
	 * Test the compatibility of the Playstation 3 with the PNG format.
	 */
	@Test
	public void testPlaystationImagePngCompatibility() {
		RendererConfiguration conf = RendererConfiguration.getRendererConfigurationByName("Playstation 3");
		assertNotNull("No renderer named \"Playstation 3\" found.", conf);

		// Construct JPG information
		DLNAMediaInfo info = new DLNAMediaInfo();
		info.setContainer("png");
		Format format = new PNG();
		format.match("test.png");
		assertEquals("PS3 is reported to be incompatible with PNG", true,
				conf.isCompatible(info, format));
	}


	/**
	 * Test the compatibility of the Playstation 3 with the TIFF format.
	 */
	@Test
	public void testPlaystationImageTiffCompatibility() {
		RendererConfiguration conf = RendererConfiguration.getRendererConfigurationByName("Playstation 3");
		assertNotNull("No renderer named \"Playstation 3\" found.", conf);

		// Construct JPG information
		DLNAMediaInfo info = new DLNAMediaInfo();
		info.setContainer("tiff");
		Format format = new TIF();
		format.match("test.tiff");
		assertEquals("PS3 is reported to be incompatible with TIFF", true,
				conf.isCompatible(info, format));
	}

	/**
	 * Test the compatibility of the Playstation 3 with the MP3 format.
	 */
	@Test
	public void testPlaystationAudioMp3Compatibility() {
		RendererConfiguration conf = RendererConfiguration.getRendererConfigurationByName("Playstation 3");
		assertNotNull("No renderer named \"Playstation 3\" found.", conf);

		// Construct regular two channel MP3 information
		DLNAMediaInfo info = new DLNAMediaInfo();
		info.setContainer("mp3");
		info.setMimeType(HTTPResource.AUDIO_MP3_TYPEMIME);
		DLNAMediaAudio audio = new DLNAMediaAudio();
		audio.setNrAudioChannels(2);
		ArrayList<DLNAMediaAudio> audioCodes = new ArrayList<DLNAMediaAudio>();
		audioCodes.add(audio);
		info.setAudioCodes(audioCodes);
		Format format = new MP3();
		format.match("test.mp3");
		assertEquals("PS3 is reported to be incompatible with MP3", true,
				conf.isCompatible(info, format));

		// Construct five channel MP3 that the PS3 does not support natively
		audio.setNrAudioChannels(5);
		assertEquals("PS3 is reported to be incompatible with MP3", false,
				conf.isCompatible(info, format));
	}

	/**
	 * Test the compatibility of the Playstation 3 with the MPG format.
	 */
	@Test
	public void testPlaystationVideoMpgCompatibility() {
		RendererConfiguration conf = RendererConfiguration.getRendererConfigurationByName("Playstation 3");
		assertNotNull("No renderer named \"Playstation 3\" found.", conf);

		// Construct regular two channel MPG information
		DLNAMediaInfo info = new DLNAMediaInfo();
		info.setContainer("avi");
		DLNAMediaAudio audio = new DLNAMediaAudio();
		audio.setCodecA("ac3");
		audio.setNrAudioChannels(5);
		ArrayList<DLNAMediaAudio> audioCodes = new ArrayList<DLNAMediaAudio>();
		audioCodes.add(audio);
		info.setAudioCodes(audioCodes);
		info.setCodecV("mp4");
		Format format = new MPG();
		format.match("test.avi");
		assertEquals("PS3 is reported to be incompatible with MPG", true,
				conf.isCompatible(info, format));

		// Construct MPG with wmv codec that the PS3 does not support natively
		info.setCodecV("wmv");
		assertEquals("PS3 is reported to be compatible with MPG with wmv codec", false,
				conf.isCompatible(info, format));
	}

	/**
	 * Test the compatibility of the Playstation 3 with the MPG format.
	 */
	@Test
	public void testPlaystationVideoMkvCompatibility() {
		RendererConfiguration conf = RendererConfiguration.getRendererConfigurationByName("Playstation 3");
		assertNotNull("No renderer named \"Playstation 3\" found.", conf);

		// Construct MKV information
		DLNAMediaInfo info = new DLNAMediaInfo();
		info.setContainer("mkv");
		DLNAMediaAudio audio = new DLNAMediaAudio();
		audio.setCodecA("ac3");
		audio.setNrAudioChannels(5);
		ArrayList<DLNAMediaAudio> audioCodes = new ArrayList<DLNAMediaAudio>();
		audioCodes.add(audio);
		info.setAudioCodes(audioCodes);
		info.setCodecV("mp4");
		Format format = new MPG();
		format.match("test.mkv");
		assertEquals("PS3 is reported to be incompatible with MKV", false,
				conf.isCompatible(info, format));
	}

}
